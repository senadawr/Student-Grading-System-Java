package com.grading.view;

import com.grading.model.Student;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddStudentDialog extends Dialog<Student> {
    private TextField nameField;
    private TextField itec104Field;
    private TextField gec106Field;
    private TextField soslitField;
    private TextField cmsc202Field;
    private TextField cmsc203Field;
    private TextField itec105Field;
    private TextField math24Field;
    private TextField pathfit3Field;

    public AddStudentDialog() {
        setTitle("Add New Student");
        setHeaderText("Enter student details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff;");

        nameField = new TextField();
        itec104Field = new TextField();
        gec106Field = new TextField();
        soslitField = new TextField();
        cmsc202Field = new TextField();
        cmsc203Field = new TextField();
        itec105Field = new TextField();
        math24Field = new TextField();
        pathfit3Field = new TextField();

        grid.add(createLabel("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(createLabel("ITEC 104:"), 0, 1);
        grid.add(itec104Field, 1, 1);
        grid.add(createLabel("GEC 106:"), 0, 2);
        grid.add(gec106Field, 1, 2);
        grid.add(createLabel("SOSLIT:"), 0, 3);
        grid.add(soslitField, 1, 3);
        grid.add(createLabel("CMSC 202:"), 0, 4);
        grid.add(cmsc202Field, 1, 4);
        grid.add(createLabel("CMSC 203:"), 0, 5);
        grid.add(cmsc203Field, 1, 5);
        grid.add(createLabel("ITEC 105:"), 0, 6);
        grid.add(itec105Field, 1, 6);
        grid.add(createLabel("MATH 24:"), 0, 7);
        grid.add(math24Field, 1, 7);
        grid.add(createLabel("PATHFIT 3:"), 0, 8);
        grid.add(pathfit3Field, 1, 8);

        getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    Student student = new Student(
                        nameField.getText(),
                        Double.parseDouble(itec104Field.getText()),
                        Double.parseDouble(gec106Field.getText()),
                        Double.parseDouble(soslitField.getText()),
                        Double.parseDouble(cmsc202Field.getText()),
                        Double.parseDouble(cmsc203Field.getText()),
                        Double.parseDouble(itec105Field.getText()),
                        Double.parseDouble(math24Field.getText()),
                        Double.parseDouble(pathfit3Field.getText())
                    );
                    student.updateGwa();
                    return student;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setContentText("Please enter valid numbers for grades.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #333333; -fx-font-family: 'Segoe UI', sans-serif;");
        return label;
    }
}