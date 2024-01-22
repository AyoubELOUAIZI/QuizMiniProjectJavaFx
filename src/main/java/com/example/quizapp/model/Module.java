package com.example.quizapp.model;

import java.util.List;

public class Module {
    private int moduleId;
    private String moduleName;
    private String modulePassword; // New field for module password
    private List<Quiz> quizzes;
    private int teacherId;

    // Constructors
    public Module(int moduleId, String moduleName, String modulePassword, List<Quiz> quizzes, int teacherId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.modulePassword = modulePassword;
        this.quizzes = quizzes;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModulePassword() {
        return modulePassword;
    }

    public void setModulePassword(String modulePassword) {
        this.modulePassword = modulePassword;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    // Additional methods if needed
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public void removeQuiz(Quiz quiz) {
        quizzes.remove(quiz);
    }

    // You can add more methods based on your application's requirements
}
