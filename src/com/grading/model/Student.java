package com.grading.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private StringProperty name;
    private Map<String, Double> courseGrades;
    private DoubleProperty gwa;

    public Student(String name, double itec104, double gec106, double soslit, double cmsc202, double cmsc203, double itec105, double math24, double pathfit3) {
        this.name = new SimpleStringProperty(name);
        this.courseGrades = new HashMap<>();
        this.gwa = new SimpleDoubleProperty(0.0);

        // initialize course grades
        courseGrades.put("ITEC 104", itec104);
        courseGrades.put("GEC 106", gec106);
        courseGrades.put("SOSLIT", soslit);
        courseGrades.put("CMSC 202", cmsc202);
        courseGrades.put("CMSC 203", cmsc203);
        courseGrades.put("ITEC 105", itec105);
        courseGrades.put("MATH 24", math24);
        courseGrades.put("PATHFIT 3", pathfit3);

        calculateGwa();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getGwa() {
        return gwa.get();
    }

    public DoubleProperty gwaProperty() {
        return gwa;
    }

    // getter methods for each course
    public double getItec104() {
        return courseGrades.get("ITEC 104");
    }

    public double getGec106() {
        return courseGrades.get("GEC 106");
    }

    public double getSoslit() {
        return courseGrades.get("SOSLIT");
    }

    public double getCmsc202() {
        return courseGrades.get("CMSC 202");
    }

    public double getCmsc203() {
        return courseGrades.get("CMSC 203");
    }

    public double getItec105() {
        return courseGrades.get("ITEC 105");
    }

    public double getMath24() {
        return courseGrades.get("MATH 24");
    }

    public double getPathfit3() {
        return courseGrades.get("PATHFIT 3");
    }

    // property methods for each course
    public DoubleProperty itec104Property() {
        return new SimpleDoubleProperty(courseGrades.get("ITEC 104"));
    }

    public DoubleProperty gec106Property() {
        return new SimpleDoubleProperty(courseGrades.get("GEC 106"));
    }

    public DoubleProperty soslitProperty() {
        return new SimpleDoubleProperty(courseGrades.get("SOSLIT"));
    }

    public DoubleProperty cmsc202Property() {
        return new SimpleDoubleProperty(courseGrades.get("CMSC 202"));
    }

    public DoubleProperty cmsc203Property() {
        return new SimpleDoubleProperty(courseGrades.get("CMSC 203"));
    }

    public DoubleProperty itec105Property() {
        return new SimpleDoubleProperty(courseGrades.get("ITEC 105"));
    }

    public DoubleProperty math24Property() {
        return new SimpleDoubleProperty(courseGrades.get("MATH 24"));
    }

    public DoubleProperty pathfit3Property() {
        return new SimpleDoubleProperty(courseGrades.get("PATHFIT 3"));
    }

    // calculate GWA
    private void calculateGwa() {
        double totalUnits = 0.0;
        double weightedSum = 0.0;

        for (Map.Entry<String, Double> entry : courseGrades.entrySet()) {
            double grade = entry.getValue();
            int units = getCourseUnits(entry.getKey());

            totalUnits += units;
            weightedSum += grade * units;
        }

        if (totalUnits > 0) {
            gwa.set(weightedSum / totalUnits);
        } else {
            gwa.set(0.0);
        }
    }

    // update GWA
    public void updateGwa() {
        calculateGwa();
    }

    // get course units
    private int getCourseUnits(String courseCode) {
        switch (courseCode) {
            case "ITEC 104":
            case "GEC 106":
            case "SOSLIT":
            case "CMSC 202":
            case "CMSC 203":
            case "ITEC 105":
            case "MATH 24":
                return 3;
            case "PATHFIT 3":
                return 2;
            default:
                return 0;
        }
    }
}