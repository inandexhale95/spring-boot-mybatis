package com.example.quizmybatis.controller;

import com.example.quizmybatis.entity.Quiz;
import com.example.quizmybatis.form.QuizForm;
import com.example.quizmybatis.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ModelAttribute
    public QuizForm quizForm() {
        QuizForm quizForm = new QuizForm();
        quizForm.setNewQuiz(true);

        return quizForm;
    }

    @GetMapping("")
    public String index(QuizForm quizForm, Model model) {
        List<Quiz> quizList = quizService.getQuizList();

        quizForm.setNewQuiz(true);
        model.addAttribute("quizList", quizList);

        return "index";
    }

    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Quiz quiz = makeQuiz(quizForm);

        if (!bindingResult.hasErrors()) {
            quizService.createQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "퀴즈 입력 성공");
            return "redirect:/quiz";
        }

        return index(quizForm, model);
    }

    @GetMapping("/{id}")
    public String showUpdate(@PathVariable int id, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        QuizForm quizForm = makeQuizForm(quiz);

        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title", "변경 폼");

        return "index";
    }

    @PostMapping("/update")
    public String update(@Validated QuizForm quizForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Quiz quiz = makeQuiz(quizForm);

        if (!bindingResult.hasErrors()) {
            model.addAttribute("complete", "퀴즈 수정 완료");

            quizService.updateQuiz(quiz);
            return "redirect:/quiz";
        } else {
            makeUpdateModel(quizForm, model);
            return "index";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            quizService.deleteQuizById(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        redirectAttributes.addFlashAttribute("complete", "퀴즈 삭제 완료");
        return "redirect:/quiz";
    }

    @GetMapping("/play")
    public String play(Model model) {
        Quiz quiz = quizService.getRandomQuiz();
        model.addAttribute("quiz", quiz);

        return "play";
    }

    @PostMapping("/check")
    public String check(@RequestParam Boolean answer, Model model) {

        if (answer) {
            model.addAttribute("answer", "정답입니다!!!");
        } else {
            model.addAttribute("answer", "오답입니다...");
        }

        return "check";
    }

    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm quizForm = new QuizForm();
        quizForm.setId(quiz.getId());
        quizForm.setQuestion(quiz.getQuestion());
        quizForm.setAnswer(quiz.getAnswer());
        quizForm.setAuthor(quiz.getAuthor());
        quizForm.setNewQuiz(false);
        return quizForm;
    }

    private void makeUpdateModel(QuizForm quizForm, Model model) {
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title", "변경 폼");
    }
}
