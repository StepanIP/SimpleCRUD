package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Order;
import org.example.simplecrud.entity.Payment;
import org.example.simplecrud.entity.enums.PeymentMethod;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDao extends Dao<Payment>{

    private final static String SELECT_ALL = """
            Select * from payment
            """;

    private final String FIND_BY_ID = """
            SELECT * from payment where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE payment
            SET payment_method = ?,
                amount = ?,
                order_id = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into payment(id, payment_method, amount, order_id)  VALUES 
             (?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from payment where id = ?
            """;

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                payments.add(Payment.builder()
                        .id(resultSet.getInt("id"))
                        .peymentMethod(PeymentMethod.valueOf(resultSet.getString("payment_method").replaceAll(" ", "_").toUpperCase()))
                        .amount(resultSet.getDouble("amount"))
                        .order((Order) DaoFactory.createDao(Order.class).findById(resultSet.getInt("order_id"),connection).get())
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return payments;
    }

    @Override
    public Optional<Payment> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Payment.builder()
                        .id(resultSet.getInt("id"))
                        .peymentMethod(PeymentMethod.valueOf(resultSet.getString("payment_method").replaceAll(" ", "_").toUpperCase()))
                        .amount(resultSet.getDouble("amount"))
                        .order((Order) DaoFactory.createDao(Order.class).findById(resultSet.getInt("order_id"),connection).get())
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
    public int update(Payment entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.setString(1, entity.getPeymentMethod().toString());
            preparedStatement.setDouble(2, entity.getAmount());
            preparedStatement.setInt(3, entity.getOrder().getId());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Payment entity , Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getPeymentMethod().toString());
            preparedStatement.setDouble(3, entity.getAmount());
            preparedStatement.setInt(4, entity.getOrder().getId());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
