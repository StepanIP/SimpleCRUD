package org.example.simplecrud.dao;

import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Dao<T> {

    static String SELECT_ALL = "SELECT id from defoult";

     public List<T> findAll(){
        List<T> generic = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                findById(id, connection).ifPresent(generic::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generic;
    }

    abstract Optional<T> findById(Integer id, Connection connection);

    abstract boolean delete(Integer id, Connection connection);

    abstract int update(T entity, Connection connection);

    abstract boolean save(T entity, Connection connection);
}
