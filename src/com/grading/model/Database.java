package com.grading.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

    public Database(String dbPath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Student> loadAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Student student = new Student(
                    rs.getString("name"),
                    rs.getDouble("itec104"),
                    rs.getDouble("gec106"),
                    rs.getDouble("soslit"),
                    rs.getDouble("cmsc202"),
                    rs.getDouble("cmsc203"),
                    rs.getDouble("itec105"),
                    rs.getDouble("math24"),
                    rs.getDouble("pathfit3")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    public void saveStudent(Student student) {
        String query = "INSERT INTO students (name, itec104, gec106, soslit, cmsc202, cmsc203, itec105, math24, pathfit3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setDouble(2, student.getItec104());
            pstmt.setDouble(3, student.getGec106());
            pstmt.setDouble(4, student.getSoslit());
            pstmt.setDouble(5, student.getCmsc202());
            pstmt.setDouble(6, student.getCmsc203());
            pstmt.setDouble(7, student.getItec105());
            pstmt.setDouble(8, student.getMath24());
            pstmt.setDouble(9, student.getPathfit3());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clearAllStudents() {
        String query = "DELETE FROM students";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error clearing students: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    itec104 REAL NOT NULL,
                    gec106 REAL NOT NULL,
                    soslit REAL NOT NULL,
                    cmsc202 REAL NOT NULL,
                    cmsc203 REAL NOT NULL,
                    itec105 REAL NOT NULL,
                    math24 REAL NOT NULL,
                    pathfit3 REAL NOT NULL
                )
            """);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}