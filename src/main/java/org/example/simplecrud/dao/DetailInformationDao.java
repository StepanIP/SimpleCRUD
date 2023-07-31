package org.example.simplecrud.dao;

import org.example.simplecrud.entity.Cart;
import org.example.simplecrud.entity.DetailInformation;
import org.example.simplecrud.entity.enums.Country;
import org.example.simplecrud.factories.DaoFactory;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetailInformationDao extends Dao<DetailInformation> {

    private final static String SELECT_ALL = """
            Select * from detail_information
            """;
    private final String FIND_BY_ID = """
            SELECT * from detail_information where id = ?;
            """;

    private final String UPDATE_DATA = """
            UPDATE detail_information
            SET age = ?,
                country = ?,
                delivery_address = ?,
                cart_id = ?
                WHERE id = ?;
            """;

    private final String SAVE_DATA = """
            insert into detail_information(id, age, country, delivery_address, cart_id)  VALUES 
             (?,?,?,?,?);
            """;

    private final String DELETE = """
            DELETE from detail_information where id = ?
            """;

    @Override
    public List<DetailInformation> findAll() {
        List<DetailInformation> detailInformation = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                detailInformation.add(DetailInformation.builder()
                        .id(resultSet.getInt("id"))
                        .age(resultSet.getInt("age"))
                        .country(Country.valueOf(resultSet.getString("country").replaceAll(" ","_").toUpperCase()))
                        .deliveryAddress(resultSet.getString("delivery_address"))
                        .cart((Cart) DaoFactory.createDao(Cart.class)
                                .findById(resultSet
                                        .getInt("cart_id"), connection).get())
                        .build()
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return detailInformation;
    }

    @Override
    public Optional<DetailInformation> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(DetailInformation.builder()
                        .id(resultSet.getInt("id"))
                        .age(resultSet.getInt("age"))
                        .country(Country.valueOf(resultSet.getString("country").replaceAll(" ","_").toUpperCase()))
                        .deliveryAddress(resultSet.getString("delivery_address"))
                        .cart((Cart) DaoFactory.createDao(Cart.class).findById(resultSet.getInt("cart_id"), connection).get())
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
    public int update(DetailInformation entity, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA)) {
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.setInt(1, entity.getAge());
            preparedStatement.setString(2, entity.getCountry().toString());
            preparedStatement.setString(3, entity.getDeliveryAddress());
            preparedStatement.setInt(4, entity.getCart().getId());
            preparedStatement.executeQuery();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public boolean save(DetailInformation entity , Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getAge());
            preparedStatement.setString(3, entity.getCountry().toString());
            preparedStatement.setString(4, entity.getDeliveryAddress());
            preparedStatement.setInt(5, entity.getCart().getId());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
