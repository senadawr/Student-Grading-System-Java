package com.grading;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.grading.model.Database;
import com.grading.view.MainView;

public class Main extends Application {
    private Database database;

    @Override
    public void start(Stage primaryStage) {
        try {
            database = new Database("students.db");
            MainView mainView = new MainView(database);
            Scene scene = mainView.getScene();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Student Grading System");
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    // close connection if closed
    public void stop() {
        if (database != null) {
            database.closeConnection();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Launching application...");
            launch(args);
        } catch (Exception e) {
            System.err.println("Fatal error in main(): ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}