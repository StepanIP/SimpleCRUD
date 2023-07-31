package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Order;
import org.example.simplecrud.entity.Product;
import org.example.simplecrud.entity.User;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao extends Dao<Order>{

    private final static String SELECT_ALL = """
            Select * from orders
            """;
    private final String FIND_BY_ID = """
            SELECT * from orders where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE orders
            SET state = ?,
                date_ordered = ?,
                date_shifted = ?,
                product_id = ?,
                user_id = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into orders(id, state, date_ordered, date_shifted, product_id, user_id)  VALUES 
             (?,?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from orders where id = ?
            """;

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt("id"))
                        .state(resultSet.getString("state"))
                        .dateOrdered(resultSet.getDate("date_ordered").toLocalDate())
                        .dateShifted(resultSet.getDate("date_shifted").toLocalDate())
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"), connection).get())
                        .user((User) DaoFactory.createDao(User.class).findById(resultSet.getInt("user_id"), connection).get())
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Order.builder()
                        .id(resultSet.getInt("id"))
                        .state(resultSet.getString("state"))
                        .dateOrdered(resultSet.getDate("date_ordered").toLocalDate())
                        .dateShifted(resultSet.getDate("date_shifted").toLocalDate())
                        .product((Product) DaoFactory.createDao(Product.class).findById(resultSet.getInt("product_id"), connection).get())
                        .user((User) DaoFactory.createDao(User.class).findById(resultSet.getInt("user_id"), connection).get())
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
    public int update(Order entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(6, entity.getId());
            preparedStatement.setString(1, entity.getState());
            preparedStatement.setDate(2, Date.valueOf(entity.getDateOrdered()));
            preparedStatement.setDate(3, Date.valueOf(entity.getDateShifted()));
            preparedStatement.setInt(4, entity.getProduct().getId());
            preparedStatement.setInt(5, entity.getUser().getId());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Order entity , Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getState());
            preparedStatement.setDate(3, Date.valueOf(entity.getDateOrdered()));
            preparedStatement.setDate(4, Date.valueOf(entity.getDateShifted()));
            preparedStatement.setInt(5, entity.getProduct().getId());
            preparedStatement.setInt(6, entity.getUser().getId());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
