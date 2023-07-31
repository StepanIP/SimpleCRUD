package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Supplier;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierDao extends Dao<Supplier> {

    private final static String SELECT_ALL = """
            Select * from suppliers
            """;
    private final String FIND_BY_ID = """
            SELECT * from suppliers where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE suppliers
            SET company_name = ?,
                contact_person = ?,
                address = ?,
                email = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into suppliers(id, company_name, contact_person, address, email)  VALUES 
             (?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from suppliers where id = ?
            """;

    @Override
    public List<Supplier> findAll() {
            List<Supplier> suppliers = new ArrayList<>();
            try (Connection connection = ConnectionManager.get();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    suppliers.add(Supplier.builder()
                            .id(resultSet.getInt("id"))
                            .companyName(resultSet.getString("company_name"))
                            .contactPerson(resultSet.getString("contact_person"))
                            .address(resultSet.getString("address"))
                            .email(resultSet.getString("email"))
                            .build()
                    );
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return suppliers;
        }

    @Override
    public Optional<Supplier> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Supplier.builder()
                        .id(resultSet.getInt("id"))
                        .companyName(resultSet.getString("company_name"))
                        .contactPerson(resultSet.getString("contact_person"))
                        .address(resultSet.getString("address"))
                        .email(resultSet.getString("email"))
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
    public int update(Supplier entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.setString(1, entity.getCompanyName());
            preparedStatement.setString(2, entity.getContactPerson());
            preparedStatement.setString(3, entity.getAddress());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(Supplier entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getCompanyName());
            preparedStatement.setString(3, entity.getContactPerson());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
