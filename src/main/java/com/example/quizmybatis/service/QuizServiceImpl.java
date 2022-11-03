package com.example.quizmybatis.service;

import com.example.quizmybatis.entity.Quiz;
import com.example.quizmybatis.mapper.QuizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private QuizMapper quizMapper;

    @Autowired
    public QuizServiceImpl(QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    @Override
    public boolean createQuiz(Quiz quiz) {
        return quizMapper.insert(quiz);
    }

    @Override
    public List<Quiz> getQuizList() {
        return quizMapper.getAll();
    }

    @Override
    public Quiz getQuizById(int id) {
        Optional<Quiz> optQuiz = quizMapper.getById(id);

        return optQuiz.orElse(null);
    }

    @Override
    public boolean updateQuiz(Quiz quiz) {
        return quizMapper.update(quiz);
    }

    @Override
    public boolean deleteQuizById(int id) {
        Optional<Quiz> optQuiz = quizMapper.getById(id);

        if (optQuiz.isPresent()) {
            quizMapper.delete(optQuiz.get().getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Quiz getRandomQuiz() {
        Optional<Quiz> optQuiz = quizMapper.getRandom();

        return optQuiz.orElse(null);
    }
}
