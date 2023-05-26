package jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import org.postgresql.PGEnvironment;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

@Getter
@Setter
public class CustomDataSource implements DataSource {
    private static volatile CustomDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;

    private CustomDataSource(String driver, String url, String password, String name) {
        this.driver = driver;
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public static CustomDataSource getInstance() {
        if (instance == null) {
            try {
                File propsFile = new File("src\\main\\resources\\app.properties");
                FileInputStream fis = new FileInputStream(propsFile.getAbsolutePath());
                Properties props = new Properties();
                props.load(fis);

                String driver = props.getProperty("postgres.driver");
                String url = props.getProperty("postgres.url");
                String password = props.getProperty("postgres.password");
                String name = props.getProperty("postgres.name");

                instance = new CustomDataSource(driver, url, password, name);

                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.name, this.password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
