// Student.java
package com.example.quizapp.model;

import java.util.List;

public class Student extends User {
    private String studentCode; // or 'cne' or any other identifier for students
    private List<Integer> moduleIds;

    // Constructor
    public Student(int userId, String firstname, String lastname, String email, String password, String studentCode, List<Integer> moduleIds) {
        super(userId, firstname, lastname, email, password, "student");
        this.studentCode = studentCode;
        this.moduleIds = moduleIds;
    }

    // Getter and Setter for studentCode
    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    // Getter and Setter for moduleIds
    public List<Integer> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Integer> moduleIds) {
        this.moduleIds = moduleIds;
    }

    // Additional methods if needed
    public void addModuleId(int moduleId) {
        moduleIds.add(moduleId);
    }

    public void removeModuleId(int moduleId) {
        moduleIds.remove(moduleId);
    }
}
