package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "INSERT INTO myusers (id, firstname, lastname, age)" +
                "VALUES (?, ?, ?, ?);";
    private static final String updateUserSQL = "UPDATE myusers SET firstname = ?, lastname = ?, age = ? WHERE id = ?";
    private static final String deleteUser = "DELETE FROM myusers WHERE id = ?;";
    private static final String findUserByIdSQL = "SELECT * FROM myusers WHERE id = ?;";
    private static final String findUserByNameSQL = "SELECT * FROM myusers WHERE firstname = ?;";
    private static final String findAllUserSQL = "SELECT * FROM myusers;";

    public Long createUser(User user) {
        try {
            if (this.connection == null) {
                this.connection = CustomDataSource.getInstance().getConnection();
            }

            this.ps = this.connection.prepareStatement(createUserSQL);
            this.ps.setLong(1, user.getId());
            this.ps.setString(2, user.getFirstName());
            this.ps.setString(3, user.getLastName());
            this.ps.setInt(4, user.getAge());

            this.ps.execute();

            User u = this.findUserById(user.getId());

            return u.getId();

        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User findUserById(Long userId) {
        User user = null;
        try {
            if (this.connection == null) {
                this.connection = CustomDataSource.getInstance().getConnection();
            }

            this.ps = this.connection.prepareStatement(findUserByIdSQL);
            this.ps.setLong(1, userId);
            ResultSet rs = this.ps.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int age = rs.getInt("age");

                user = new User(id, firstname, lastname, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return user;
    }

    public User findUserByName(String userName) {

        User user = null;

        try {
            if (this.connection == null) {
                this.connection = CustomDataSource.getInstance().getConnection();
            }

            this.ps = this.connection.prepareStatement(findUserByNameSQL);
            this.ps.setString(1, userName);

            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int age = rs.getInt("age");

                user = new User(id, firstname, lastname, age);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return user;
    }

    public List<User> findAllUser() {
        List<User> users = null;

        try {
            if (this.connection == null) {
                this.connection = CustomDataSource.getInstance().getConnection();
            }

            this.ps = this.connection.prepareStatement(findAllUserSQL);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int age = rs.getInt("age");

                User user = new User(id, firstname, lastname, age);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return users;
    }

    public User updateUser(User user) {
        try {
            if (this.connection == null) {
                this.connection = CustomDataSource.getInstance().getConnection();
            }

            this.ps = this.connection.prepareStatement(updateUserSQL);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setLong(4, user.getId());

            ps.execute();

            return this.findUserById(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void deleteUser(Long userId) {
        try {
            if (this.connection == null) {
                this.connection = CustomDataSource.getInstance().getConnection();
            }

            this.ps = this.connection.prepareStatement(deleteUser);
            this.ps.setLong(1, userId);
            this.ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
