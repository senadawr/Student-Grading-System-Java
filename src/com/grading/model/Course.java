package com.grading.model;

public class Course {
    private String courseCode;
    private int units;

    public Course(String courseCode, int units) {
        this.courseCode = courseCode;
        this.units = units;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
} 