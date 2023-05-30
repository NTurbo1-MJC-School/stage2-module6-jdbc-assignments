package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class CustomConnector {
    public Connection getConnection(String url) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public Connection getConnection(String url, String user, String password)  {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
