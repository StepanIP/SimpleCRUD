package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Product;
import org.example.simplecrud.entity.Review;
import org.example.simplecrud.entity.User;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevievsDao extends Dao<Review>{

    private final static String SELECT_ALL = """
            Select * from reviews
            """;

    private final String FIND_BY_ID = """
            SELECT * from reviews where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE reviews
            SET user_id = ?,
                product_id = ?,
                text = ?,
                rate = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into reviews(id, user_id, product_id, text, rate)  VALUES 
             (?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from reviews where id = ?
            """;

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                reviews.add(Review.builder()
                        .id(resultSet.getInt("id"))
                        .user((User) DaoFactory.createDao(User.class).findById(resultSet.getInt("user_id"),connection).get())
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"),connection).get())
                        .text(resultSet.getString("text"))
                        .rate(resultSet.getInt("rate"))
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return reviews;
    }

    @Override
    public Optional<Review> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Review.builder()
                        .id(resultSet.getInt("id"))
                        .user((User) DaoFactory.createDao(User.class).findById(resultSet.getInt("user_id"),connection).get())
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"),connection).get())
                        .text(resultSet.getString("text"))
                        .rate(resultSet.getInt("rate"))
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
    public int update(Review entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.setInt(1, entity.getUser().getId());
            preparedStatement.setInt(2, entity.getProduct().getId());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setInt(4, entity.getRate());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Review entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getUser().getId());
            preparedStatement.setInt(3, entity.getProduct().getId());
            preparedStatement.setString(4, entity.getText());
            preparedStatement.setInt(5, entity.getRate());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
