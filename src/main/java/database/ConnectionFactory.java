package database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static volatile ConnectionFactory connectionFactory;

    private static final String URL = "jdbc:postgresql://localhost:5432/transtator_telegram_bot";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private ConnectionFactory() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();


            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ConnectionFactory getInstance() {
        ConnectionFactory localeFactory = connectionFactory;

        if (localeFactory == null) {
            synchronized (DriverManager.class) {
                localeFactory = connectionFactory;
                if (localeFactory == null) {
                    connectionFactory = localeFactory = new ConnectionFactory();
                }
            }
        }

        return localeFactory;
    }
}
