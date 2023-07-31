package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Product;
import org.example.simplecrud.entity.Supplier;
import org.example.simplecrud.entity.enums.Metal;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao extends Dao<Product> {

    private final static String SELECT_ALL = """
            Select * from product
            """;

    private final String FIND_BY_ID = """
            SELECT * from product where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE product
            SET name = ?,
                metal = ?,
                cost = ?,
                image = ?,
                supplier_id = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into product(id, name, metal, cost, image, supplier_id)  VALUES 
             (?,?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from product where id = ?
            """;


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .metal(Metal.valueOf(resultSet.getString("metal").replaceAll(" ","_").toUpperCase()))
                        .cost(resultSet.getInt("cost"))
                        .image(resultSet.getString("image"))
                        .supplier((Supplier) DaoFactory.createDao(Supplier.class)
                                .findById(resultSet
                                        .getInt("supplier_id"), connection).get())
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .metal(Metal.valueOf(resultSet.getString("metal").replaceAll(" ", "_").toUpperCase()))
                        .cost(resultSet.getInt("cost"))
                        .image(resultSet.getString("image"))
                        .supplier((Supplier) DaoFactory.createDao(Supplier.class).findById(resultSet.getInt("supplier_id"), connection).get())
                        .build()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int update(Product entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(6, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getMetal().toString());
            preparedStatement.setInt(3, entity.getCost());
            preparedStatement.setString(4, entity.getImage());
            preparedStatement.setInt(5, entity.getSupplier().getId());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Product entity , Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getMetal().toString());
            preparedStatement.setInt(4, entity.getCost());
            preparedStatement.setString(5, entity.getImage());
            preparedStatement.setInt(6, entity.getSupplier().getId());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
