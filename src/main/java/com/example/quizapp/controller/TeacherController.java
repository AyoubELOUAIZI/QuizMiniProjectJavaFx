// TeacherController.java
package com.example.quizapp.controller;

import com.example.quizapp.dao.TeacherDAO;
import com.example.quizapp.model.Teacher;
import javafx.event.ActionEvent;

import java.util.List;

public class TeacherController {
    private TeacherDAO teacherDAO;



    public TeacherController() {
        this.teacherDAO = new TeacherDAO();
    }

    public Teacher getTeacherById(int userId) {
        return teacherDAO.getTeacherById(userId);
    }

    public void createTeacher(Teacher teacher) {
        teacherDAO.createTeacher(teacher);
    }

    public void updateTeacher(Teacher updatedTeacher) {
        teacherDAO.updateTeacher(updatedTeacher);
    }

    public void deleteTeacher(int userId) {
        teacherDAO.deleteTeacher(userId);
    }

    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAllTeachers();
    }

    public void handleButtonAction(ActionEvent actionEvent) {

    }
}
