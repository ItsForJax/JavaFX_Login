package com.epds.javafx_login.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DATABASE_URL = "jdbc:sqlite:users.db";
    private static Connection conn;

    private DatabaseHelper() {
    }

    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(DATABASE_URL);
                System.out.println("Connected to the database.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return conn;
    }

    public static void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    public static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " username TEXT NOT NULL,"
                + " password TEXT NOT NULL,"
                + " mobile_number TEXT,"
                + " email TEXT,"
                + " first_name TEXT,"
                + " last_name TEXT,"
                + " middle_name TEXT,"
                + " name_extension TEXT,"
                + " birthday DATE,"
                + " sex TEXT,"
                + " isNewUser BOOL DEFAULT 1"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating users table: " + e.getMessage());
        }
    }

    public static Boolean updateUser(String username, String password, String mobileNumber, String email, String firstName, String lastName, String middleName, String nameExtension, String birthday, String sex, int isNewUser) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        List<String> updates = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (mobileNumber != null && !mobileNumber.trim().isEmpty()) {
            updates.add("mobile_number = ?");
            params.add(mobileNumber);
        }
        if (email != null && !email.trim().isEmpty()) {
            updates.add("email = ?");
            params.add(email);
        }
        if (firstName != null && !firstName.trim().isEmpty()) {
            updates.add("first_name = ?");
            params.add(firstName);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            updates.add("last_name = ?");
            params.add(lastName);
        }
        if (middleName != null && !middleName.trim().isEmpty()) {
            updates.add("middle_name = ?");
            params.add(middleName);
        }
        if (nameExtension != null && !nameExtension.trim().isEmpty()) {
            updates.add("name_extension = ?");
            params.add(nameExtension);
        }
        if (birthday != null && !birthday.trim().isEmpty()) {
            updates.add("birthday = ?");
            params.add(birthday);
        }
        if (sex != null && !sex.trim().isEmpty()) {
            updates.add("sex = ?");
            params.add(sex);
        }
        updates.add("isNewUser = ?");
        params.add(isNewUser);

        if (updates.isEmpty()) {
            System.out.println("No fields to update.");
            return Boolean.FALSE;
        }

        sql.append(String.join(", ", updates));
        sql.append(" WHERE username = ? AND password = ?");
        params.add(username);
        params.add(password);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    public static boolean registerUser(String username, String password) {
        // Check if the user already exists
        if (userExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    private static boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Error checking if user exists: " + e.getMessage());
            return false;
        }
    }


    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error logging in user: " + e.getMessage());
            return false;
        }
    }

    public static boolean isNewUser(String username) {
        String sql = "SELECT isNewUser FROM users WHERE username = ?";
        boolean isNewUser = true;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                isNewUser = rs.getBoolean("isNewUser");
            }
        } catch (SQLException e) {
            System.out.println("Error checking if username is a new user: " + e.getMessage());
        }

        return isNewUser;
    }
}
