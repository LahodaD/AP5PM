package com.example.lahodazapocet;

import java.io.Serializable;
import java.util.List;

public class QuestionList implements Serializable {
    private static final long serialVersionUID = -601353394084230484L;
    private String category;
    private String id;
    private String correctAnswer;
    private List<String> incorrectAnswers;
    private String question;
    private List<String> tags;
    private String type;
    private String difficulty;
    private List<String> regions;
    boolean isNiche;

    public QuestionList(String category, String id, String correctAnswer, List<String> incorrectAnswers,
                        String question, List<String> tags, String type,
                        String difficulty, List<String> regions, boolean isNiche) {
        this.category = category;
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.question = question;
        this.tags = tags;
        this.type = type;
        this.difficulty = difficulty;
        this.regions = regions;
        this.isNiche = isNiche;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<String> getRegions() {
        return regions;
    }

    public boolean isNiche() {
        return isNiche;
    }




}
