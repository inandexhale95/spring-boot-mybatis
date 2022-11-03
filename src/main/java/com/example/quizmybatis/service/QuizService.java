package com.example.quizmybatis.service;

import com.example.quizmybatis.entity.Quiz;

import java.util.List;

public interface QuizService {
    boolean createQuiz(Quiz quiz);
    List<Quiz> getQuizList();
    Quiz getQuizById(int id);
    boolean updateQuiz(Quiz quiz);
    boolean deleteQuizById(int id);
    Quiz getRandomQuiz();
}
