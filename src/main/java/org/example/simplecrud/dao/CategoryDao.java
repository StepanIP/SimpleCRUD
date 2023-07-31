package org.example.simplecrud.dao;



import org.example.simplecrud.entity.Category;
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

public class CategoryDao extends Dao<Category>{

    private final String FIND_BY_ID = """
            SELECT * from category where id = ?;
            """;

    private final String FIND_LIST_TO_CATEGORY = """
            SELECT * from product 
            join category_product cp on product.id = cp.product_id
            join category c on cp.category_id = c.id
            where c.id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE category
            SET name = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into category(id, name)  VALUES 
             (?,?);
            """;

    private final String DELETE = """
            DELETE from category where id = ?
            """;

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM category");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                findById(id, connection).ifPresent(categories::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }


    @Override
    public Optional<Category> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
             PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_LIST_TO_CATEGORY)) {

            preparedStatement.setInt(1, id);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet1.next()){
                products.add(Product.builder()
                        .id(resultSet1.getInt("id"))
                        .name(resultSet1.getString("name"))
                        .metal(Metal.valueOf(resultSet1.getString("metal").replaceAll(" ", "_").toUpperCase()))
                        .cost(resultSet1.getInt("cost"))
                        .image(resultSet1.getString("image"))
                        .supplier((Supplier) DaoFactory.createDao(Supplier.class).findById(resultSet1.getInt("supplier_id"), connection).get())
                        .build());
            }

            if (resultSet.next()) {
                return Optional.of(Category.builder()
                        .id(resultSet.getInt("id"))
                        .category(resultSet.getString("name").replaceAll("-", "_").toUpperCase())
                        .products(products)
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
    public int update(Category entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.setString(1, entity.getCategory().toString());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Category entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getCategory().toString());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
