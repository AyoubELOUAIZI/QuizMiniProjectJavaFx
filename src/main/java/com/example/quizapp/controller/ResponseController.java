package com.example.quizapp.controller;

import com.example.quizapp.dao.ResponseDAO;
import com.example.quizapp.model.Response;

import java.util.List;

public class ResponseController {
    private final ResponseDAO responseDAO;

    public ResponseController() {
        this.responseDAO = new ResponseDAO();
    }

    public Response getResponseById(String responseId) {
        return responseDAO.getResponseById(responseId);
    }

    public void createResponse(Response response) {
        responseDAO.createResponse(response);
    }

    public void updateResponse(Response updatedResponse) {
        responseDAO.updateResponse(updatedResponse);
    }

    public void deleteResponse(String responseId) {
        responseDAO.deleteResponse(responseId);
    }

    public List<Response> getAllResponses() {
        return responseDAO.getAllResponses();
    }
}
