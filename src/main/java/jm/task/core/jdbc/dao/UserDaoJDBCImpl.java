package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users("
                    + "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(15) NOT NULL ,"
                    + "lastName VARCHAR(15) NOT NULL,"
                    + "age TINYINT NOT NULL)");
            statement.executeUpdate("ALTER TABLE users AUTO_INCREMENT=10001");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (name,lastName,age) VALUES(?,?,?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("name"),
                        rs.getString("lastName"), rs.getByte("age")));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE Users");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
