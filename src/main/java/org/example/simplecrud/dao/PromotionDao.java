package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Product;
import org.example.simplecrud.entity.Promotion;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromotionDao extends Dao<Promotion>{

    private final static String SELECT_ALL = """
            Select * from promotions
            """;

    private final String FIND_BY_ID = """
            SELECT * from promotions where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE promotions
            SET start_date = ?,
                end_date = ?,
                product_id = ?,
                discount = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into promotions(id, start_date, end_date, product_id, discount)  VALUES 
             (?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from promotions where id = ?
            """;

    @Override
    public List<Promotion> findAll() {
        List<Promotion> promotions = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                promotions.add(Promotion.builder()
                        .id(resultSet.getInt("id"))
                        .startDate(resultSet.getDate("start_date").toLocalDate())
                        .endDate(resultSet.getDate("end_date").toLocalDate())
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"), connection).get())
                        .discount(resultSet.getDouble("discount"))
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return promotions;
    }

    @Override
    public Optional<Promotion> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Promotion.builder()
                        .id(resultSet.getInt("id"))
                        .startDate(resultSet.getDate("start_date").toLocalDate())
                        .endDate(resultSet.getDate("end_date").toLocalDate())
                        .product((Product) DaoFactory.createDao(ProductDao.class).findById(resultSet.getInt("product_id"), connection).get())
                        .discount(resultSet.getDouble("discount"))
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
    public int update(Promotion entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.setDate(1, Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(2, Date.valueOf(entity.getEndDate()));
            preparedStatement.setInt(3, entity.getProduct().getId());
            preparedStatement.setDouble(4, entity.getDiscount());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Promotion entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(entity.getEndDate()));
            preparedStatement.setInt(4, entity.getProduct().getId());
            preparedStatement.setDouble(5, entity.getDiscount());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
