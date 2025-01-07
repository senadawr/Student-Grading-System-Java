package com.grading.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.grading.model.Student;
import com.grading.model.Database;
import com.grading.model.Course;
import com.grading.util.SortingAlgorithms;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.List;
import java.util.ArrayList;

public class MainView {
    private Scene scene;
    private TableView<Student> table;
    private ObservableList<Student> students;
    private Database database;
    private ObservableList<Course> courses;
    private final ObservableList<Course> defaultCourses;

    public MainView(Database database) {
        System.out.println("Initializing MainView");
        this.database = database;
        this.defaultCourses = FXCollections.observableArrayList(
            new Course("ITEC 104", 3),
            new Course("GEC 106", 3),
            new Course("SOSLIT", 3),
            new Course("CMSC 202", 3),
            new Course("CMSC 203", 3),
            new Course("ITEC 105", 3),
            new Course("MATH 24", 3),
            new Course("PATHFIT 3", 2)
        );
        this.courses = FXCollections.observableArrayList(defaultCourses);
        students = FXCollections.observableArrayList();
        
        // load students
        List<Student> loadedStudents = database.loadAllStudents();
        students.addAll(loadedStudents);
        System.out.println("Loaded " + loadedStudents.size() + " students");

        initializeUI();
    }

    private void initializeUI() {
        VBox mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #f4f4f9;");

        // title label
        Label titleLabel = new Label("Student Grading System");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #333333; -fx-font-family: 'Segoe UI', sans-serif;");

        // create table
        table = new TableView<>();
        table.setItems(students);
        table.setStyle("-fx-background-color: #ffffff; -fx-table-cell-border-color: transparent; -fx-font-family: 'Segoe UI', sans-serif;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(false);

        // create columns
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold;");
        nameColumn.setSortable(false);

        TableColumn<Student, Number> itec104Column = new TableColumn<>("ITEC 104");
        itec104Column.setCellValueFactory(cellData -> cellData.getValue().itec104Property());
        itec104Column.setStyle("-fx-text-fill: #333333;");
        itec104Column.setSortable(false);

        TableColumn<Student, Number> gec106Column = new TableColumn<>("GEC 106");
        gec106Column.setCellValueFactory(cellData -> cellData.getValue().gec106Property());
        gec106Column.setStyle("-fx-text-fill: #333333;");
        gec106Column.setSortable(false);

        TableColumn<Student, Number> soslitColumn = new TableColumn<>("SOSLIT");
        soslitColumn.setCellValueFactory(cellData -> cellData.getValue().soslitProperty());
        soslitColumn.setStyle("-fx-text-fill: #333333;");
        soslitColumn.setSortable(false);

        TableColumn<Student, Number> cmsc202Column = new TableColumn<>("CMSC 202");
        cmsc202Column.setCellValueFactory(cellData -> cellData.getValue().cmsc202Property());
        cmsc202Column.setStyle("-fx-text-fill: #333333;");
        cmsc202Column.setSortable(false);

        TableColumn<Student, Number> cmsc203Column = new TableColumn<>("CMSC 203");
        cmsc203Column.setCellValueFactory(cellData -> cellData.getValue().cmsc203Property());
        cmsc203Column.setStyle("-fx-text-fill: #333333;");
        cmsc203Column.setSortable(false);

        TableColumn<Student, Number> itec105Column = new TableColumn<>("ITEC 105");
        itec105Column.setCellValueFactory(cellData -> cellData.getValue().itec105Property());
        itec105Column.setStyle("-fx-text-fill: #333333;");
        itec105Column.setSortable(false);

        TableColumn<Student, Number> math24Column = new TableColumn<>("MATH 24");
        math24Column.setCellValueFactory(cellData -> cellData.getValue().math24Property());
        math24Column.setStyle("-fx-text-fill: #333333;");
        math24Column.setSortable(false);

        TableColumn<Student, Number> pathfit3Column = new TableColumn<>("PATHFIT 3");
        pathfit3Column.setCellValueFactory(cellData -> cellData.getValue().pathfit3Property());
        pathfit3Column.setStyle("-fx-text-fill: #333333;");
        pathfit3Column.setSortable(false);

        // create the raw GWA column
        TableColumn<Student, Number> rawGwaColumn = new TableColumn<>("Raw GWA");
        rawGwaColumn.setCellValueFactory(cellData -> cellData.getValue().gwaProperty());
        rawGwaColumn.setStyle("-fx-text-fill: #333333;");
        rawGwaColumn.setSortable(false);
        rawGwaColumn.setCellFactory(col -> new TableCell<Student, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item.doubleValue()));
                    if (item.doubleValue() >= 90) {
                        setStyle("-fx-text-fill: green;");
                    } else if (item.doubleValue() >= 80) {
                        setStyle("-fx-text-fill: orange;");
                    } else {
                        setStyle("-fx-text-fill: red;");
                    }
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });

        // create the converted GWA column
        TableColumn<Student, String> convertedGwaColumn = new TableColumn<>("Converted GWA");
        convertedGwaColumn.setCellValueFactory(cellData -> {
            double gwa = cellData.getValue().getGwa();
            String convertedGwa;

            if (gwa >= 99) {
                convertedGwa = "1.00 (Excellent)";
            } else if (gwa >= 96) {
                convertedGwa = "1.25 (Excellent)";
            } else if (gwa >= 93) {
                convertedGwa = "1.50 (Very Satisfactory)";
            } else if (gwa >= 90) {
                convertedGwa = "1.75 (Very Satisfactory)";
            } else if (gwa >= 87) {
                convertedGwa = "2.00 (Satisfactory)";
            } else if (gwa >= 84) {
                convertedGwa = "2.25 (Satisfactory)";
            } else if (gwa >= 81) {
                convertedGwa = "2.50 (Fairly Satisfactory)";
            } else if (gwa >= 78) {
                convertedGwa = "2.75 (Fairly Satisfactory)";
            } else if (gwa >= 75) {
                convertedGwa = "3.00 (Passed)";
            } else if (gwa >= 70) {
                convertedGwa = "4.00 (Conditional Failure)";
            } else {
                convertedGwa = "5.00 (Failed)";
            }

            return new SimpleStringProperty(convertedGwa);
        });
        convertedGwaColumn.setStyle("-fx-text-fill: #333333;");
        convertedGwaColumn.setSortable(false);

        table.getColumns().addAll(
            nameColumn, itec104Column, gec106Column, soslitColumn,
            cmsc202Column, cmsc203Column, itec105Column, math24Column, pathfit3Column,
            rawGwaColumn, convertedGwaColumn
        );

        table.getColumns().forEach(column -> column.setReorderable(false));

        // buttons
        Button addStudentButton = new Button("Add Student");
        Button sortStudentsButton = new Button("Sort Students");
        Button clearButton = new Button("Clear All");

        // button styles
        addStudentButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 5px;");
        sortStudentsButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 5px;");
        clearButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 5px;");

        // button actions
        addStudentButton.setOnAction(e -> showAddDialog());
        sortStudentsButton.setOnAction(e -> showSortingResults());
        clearButton.setOnAction(e -> clearStudents());

        // button layout
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(addStudentButton, sortStudentsButton, clearButton);

        // add to main container
        mainContainer.getChildren().addAll(titleLabel, buttonLayout, table);
        VBox.setVgrow(table, Priority.ALWAYS);

        scene = new Scene(mainContainer, 800, 600);
    }

    private void showAddDialog() {
        System.out.println("Opening add student dialog");
        AddStudentDialog dialog = new AddStudentDialog();
        dialog.showAndWait().ifPresent(student -> {
            System.out.println("Adding student: " + student.getName());
            students.add(student);
            database.saveStudent(student);
            System.out.println("Student added to database");
        });
    }

    private void clearStudents() {
        System.out.println("Clearing all students");
        database.clearAllStudents();
        students.clear();
        System.out.println("All students cleared from the database and UI");
    }

    private void showSortingResults() {
        if (students.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Students");
            alert.setHeaderText("Cannot Sort");
            alert.setContentText("Please add some students before sorting.");
            alert.showAndWait();
            return;
        }

        List<Student> sortedList = new ArrayList<>(students);

        SortingAlgorithms.heapSort(sortedList);

        SortResultDialog dialog = new SortResultDialog(sortedList);
        dialog.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }
}