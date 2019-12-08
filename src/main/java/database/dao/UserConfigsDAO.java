package database.dao;

import database.ConnectionFactory;
import database.User;
import database.UserConfigs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserConfigsDAO implements DAO<UserConfigs> {

    private final String CREATE_URL = "INSERT INTO " +
            "public.user_configs(user_id, interface_lang, from_lang, to_lang, speech_lang) " +
            "VALUES(?, ?, ?, ?, ?)";
    private final String READ_URL = "SELECT * FROM public.user_configs WHERE user_id = ?";
    private final String READ_ALL_URL = "SELECT * FROM public.user_configs";
    private final String UPDATE_URL = "UPDATE public.user_configs SET " +
            "interface_lang = ?, from_lang = ?, to_lang = ?, speech_lang = ? " +
            "WHERE user_id = ?";
    private final String DELETE_URL = "DELETE FROM public.user_configs WHERE user_id = ?";

    private ConnectionFactory connectionFactory;

    public UserConfigsDAO() {
        connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public void add(UserConfigs item) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_URL);
        ) {

            preparedStatement.setInt(1, item.getUser().getId());
            preparedStatement.setString(2, item.getInterfaceLang());
            preparedStatement.setString(3, item.getFromLang());
            preparedStatement.setString(4, item.getToLang());
            preparedStatement.setString(5, item.getSpeechLang());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserConfigs item) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_URL);
        ) {
            preparedStatement.setString(1, item.getInterfaceLang());
            preparedStatement.setString(2, item.getFromLang());
            preparedStatement.setString(3, item.getToLang());
            preparedStatement.setString(4, item.getSpeechLang());
            preparedStatement.setInt(5, item.getUser().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UserConfigs item) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_URL);
        ) {
            preparedStatement.setInt(1, item.getUser().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserConfigs get(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(READ_URL);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            UserConfigs userConfigs = new UserConfigs(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    null
            );

            return userConfigs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<UserConfigs> getAll() {
        List<UserConfigs> userConfigsList = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_URL);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            UserConfigs userConfigs;
            while (resultSet.next()) {
                userConfigs = new UserConfigs(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        new User(resultSet.getInt(1))
                );

                userConfigsList.add(userConfigs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userConfigsList;
    }
}
