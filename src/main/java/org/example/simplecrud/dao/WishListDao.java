package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Product;
import org.example.simplecrud.entity.User;
import org.example.simplecrud.entity.WishList;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WishListDao extends Dao<WishList>{

    private final static String SELECT_ALL = """
            Select * from wishlist
            """;

    private final String FIND_BY_ID = """
            SELECT * from wishlist where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE wishlist
            SET product_id = ?,
                user_id = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into wishlist(id, product_id, user_id) VALUES 
             (?,?,?);
            """;

    private final String DELETE = """
            DELETE from wishlist where id = ?
            """;

    @Override
    public List<WishList> findAll() {
        List<WishList> wishLists = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                wishLists.add(WishList.builder()
                        .id(resultSet.getInt("id"))
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"),connection).get())
                        .user((User) DaoFactory.createDao(User.class).findById(resultSet.getInt("user_id"),connection).get())
                        .build());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return wishLists;
    }

    @Override
    public Optional<WishList> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(WishList.builder()
                        .id(resultSet.getInt("id"))
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"),connection).get())
                        .user((User) DaoFactory.createDao(User.class).findById(resultSet.getInt("user_id"),connection).get())
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
    public int update(WishList entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.setInt(1, entity.getProduct().getId());
            preparedStatement.setInt(2, entity.getUser().getId());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(WishList entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getProduct().getId());
            preparedStatement.setInt(3, entity.getUser().getId());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
