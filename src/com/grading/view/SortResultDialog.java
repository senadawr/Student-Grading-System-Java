package com.grading.view;

import com.grading.model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class SortResultDialog extends Dialog<Void> {
    public SortResultDialog(List<Student> sortedStudents) {
        setTitle("Sorting Results");

        // main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f4f4f9;");

        // tree table
        TreeTableView<Object> treeTable = createTreeTable(sortedStudents);

        // add tree table to main
        mainLayout.getChildren().add(treeTable);
        VBox.setVgrow(treeTable, Priority.ALWAYS); // Make the tree table grow with the window

        // set content
        getDialogPane().setContent(mainLayout);
        getDialogPane().getButtonTypes().add(ButtonType.OK);

        // close when OK is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                close();
            }
            return null;
        });

        // make dialog resizable
        getDialogPane().setPrefSize(600, 400);
        getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        getDialogPane().setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private TreeTableView<Object> createTreeTable(List<Student> sortedStudents) {
        TreeTableView<Object> treeTable = new TreeTableView<>();
        treeTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #333333; -fx-font-family: 'Segoe UI', sans-serif;");
        ObservableList<Student> data = FXCollections.observableArrayList(sortedStudents);

        // name column
        TreeTableColumn<Object, String> nameColumn = new TreeTableColumn<>("Student Name");
        nameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Student) {
                return new SimpleStringProperty(((Student) cellData.getValue().getValue()).getName());
            } else {
                return new SimpleStringProperty(cellData.getValue().getValue().toString());
            }
        });
        nameColumn.setPrefWidth(200);
        nameColumn.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold;");
        nameColumn.setSortable(false);

        // raw GWA column
        TreeTableColumn<Object, Number> rawGwaColumn = new TreeTableColumn<>("Raw GWA");
        rawGwaColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Student) {
                return ((Student) cellData.getValue().getValue()).gwaProperty();
            } else {
                return null;
            }
        });
        rawGwaColumn.setPrefWidth(100);
        rawGwaColumn.setStyle("-fx-text-fill: #333333;");
        rawGwaColumn.setSortable(false);
        rawGwaColumn.setCellFactory(col -> new TreeTableCell<Object, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item.doubleValue()));
                    setStyle("-fx-text-fill: #333333;");
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });

        // converted GWA column
        TreeTableColumn<Object, String> convertedGwaColumn = new TreeTableColumn<>("Converted GWA");
        convertedGwaColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Student) {
                double gwa = ((Student) cellData.getValue().getValue()).getGwa();
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
            } else {
                return null;
            }
        });
        convertedGwaColumn.setPrefWidth(200);
        convertedGwaColumn.setStyle("-fx-text-fill: #333333;");
        convertedGwaColumn.setSortable(false);

        // add columns to tree table
        treeTable.getColumns().addAll(nameColumn, rawGwaColumn, convertedGwaColumn);

        // tree items for each student
        TreeItem<Object> root = new TreeItem<>(new Student("Students", 0, 0, 0, 0, 0, 0, 0, 0));
        root.setExpanded(true);
        for (Student student : sortedStudents) {
            TreeItem<Object> studentItem = new TreeItem<>(student);
            studentItem.setExpanded(false);

            // add course grades as children
            studentItem.getChildren().addAll(
                createCourseItem("ITEC 104", student.getItec104()),
                createCourseItem("GEC 106", student.getGec106()),
                createCourseItem("SOSLIT", student.getSoslit()),
                createCourseItem("CMSC 202", student.getCmsc202()),
                createCourseItem("CMSC 203", student.getCmsc203()),
                createCourseItem("ITEC 105", student.getItec105()),
                createCourseItem("MATH 24", student.getMath24()),
                createCourseItem("PATHFIT 3", student.getPathfit3())
            );

            root.getChildren().add(studentItem);
        }

        treeTable.setRoot(root);
        treeTable.setShowRoot(false);

        return treeTable;
    }

    private TreeItem<Object> createCourseItem(String courseName, double grade) {
        TreeItem<Object> courseItem = new TreeItem<>(courseName + ": " + String.format("%.2f", grade));
        courseItem.setGraphic(new Label(String.format("%.2f", grade)));
        courseItem.getGraphic().setStyle("-fx-alignment: CENTER_RIGHT;");
        return courseItem;
    }
}