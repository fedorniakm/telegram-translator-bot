package database.dao;

import database.User;
import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {

    private final String CREATE_URL = "INSERT INTO " +
            "public.user(id, user_name, first_name, last_name) " +
            "VALUES(?, ?, ?, ?)";
    private final String READ_URL = "SELECT * FROM public.user WHERE id = ?";
    private final String READ_ALL_URL = "SELECT * FROM public.user";
    private final String UPDATE_URL = "UPDATE public.user SET " +
            "user_name = ?, first_name = ?, last_name = ? " +
            "WHERE id = ?";
    private final String DELETE_URL = "DELETE FROM public.user WHERE id = ?";

    private ConnectionFactory connectionFactory;

    public UserDAO() {
        connectionFactory = ConnectionFactory.getInstance();
    }

    public void add(User user) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_URL);
        ) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_URL);
                ) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_URL)
        ) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User get(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(READ_URL);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            User user = new User(resultSet.getInt(1));
            user.setUserName(resultSet.getString(2));
            user.setFirstName(resultSet.getString(3));
            user.setLastName(resultSet.getString(4));

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_URL);
                ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            User user;
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        null
                );

                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

}
