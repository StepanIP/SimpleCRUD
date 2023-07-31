package org.example.simplecrud.dao;

import org.example.simplecrud.entity.*;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends Dao<User> {

    private final static String SELECT_ALL = """
            Select * from "user"
            """;

    private final String FIND_BY_ID = """
            SELECT * from "user" where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE "user"
            SET email = ?,
                phone_number = ?,
                password = ?,
                first_name = ?,
                surname = ?,
                detail_information_id = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into "user"(id, email, phone_number, password, first_name, surname, detail_information_id)  VALUES 
             (?,?,?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from "user" where id = ?
            """;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt("id"))
                        .email(resultSet.getString("email"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .password(resultSet.getInt("password"))
                        .firstName(resultSet.getString("first_name"))
                        .surname(resultSet.getString("surname"))
                        .detailInformation((DetailInformation) DaoFactory.createDao(DetailInformation.class)
                                .findById(resultSet
                                        .getInt("detail_information_id"), connection).get())
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return users;
    }

    @Override
    public Optional<User> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(User.builder()
                        .id(resultSet.getInt("id"))
                        .email(resultSet.getString("email"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .password(resultSet.getInt("password"))
                        .firstName(resultSet.getString("first_name"))
                        .surname(resultSet.getString("surname"))
                        .detailInformation((DetailInformation) DaoFactory.createDao(DetailInformation.class)
                                .findById(resultSet
                                        .getInt("detail_information_id"), connection).get())
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
    public int update(User entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(7, entity.getId());
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getPhoneNumber());
            preparedStatement.setInt(3, entity.getPassword());
            preparedStatement.setString(4, entity.getFirstName());
            preparedStatement.setString(5, entity.getSurname());
            preparedStatement.setInt(6, entity.getDetailInformation().getId());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(User entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setInt(4, entity.getPassword());
            preparedStatement.setString(5, entity.getFirstName());
            preparedStatement.setString(6, entity.getSurname());
            preparedStatement.setInt(7, entity.getDetailInformation().getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
