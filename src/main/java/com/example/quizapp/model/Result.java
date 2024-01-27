package com.example.quizapp.model;

// Classe pour représenter un résultat avec le nom de l'étudiant
public  class Result {
    private double mark;
    private String firstname;
    private String lastname;

    public Result(double mark, String firstname, String lastname) {
        this.mark = mark;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Getters pour accéder aux informations du résultat
    public double getMark() {
        return mark;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}

