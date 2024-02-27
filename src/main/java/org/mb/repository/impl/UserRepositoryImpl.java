package org.mb.repository.impl;

import org.mb.configuration.DatabaseConnectionConfiguration;
import org.mb.entity.User;
import org.mb.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        var sql = "INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?)";
        try (var conn = DatabaseConnectionConfiguration.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.userId());
            stmt.setString(2, user.userGuid());
            stmt.setString(3, user.userName());
            stmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public Optional<User> findById(int userId) {
        var sql = "SELECT * FROM SUSERS WHERE USER_ID = ?";
        try (var conn = DatabaseConnectionConfiguration.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var user = new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USER_GUID"),
                        rs.getString("USER_NAME"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        var sql = "SELECT * FROM SUSERS";
        try (var conn = DatabaseConnectionConfiguration.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USER_GUID"),
                        rs.getString("USER_NAME"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all users", e);
        }
        return users;
    }

    @Override
    public void deleteById(int userId) {
        var sql = "DELETE FROM SUSERS WHERE USER_ID = ?";
        try (var conn = DatabaseConnectionConfiguration.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user by ID", e);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM SUSERS";
        try (var conn = DatabaseConnectionConfiguration.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting all users", e);
        }
    }
}
