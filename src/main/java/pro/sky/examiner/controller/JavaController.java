package pro.sky.examiner.controller;

import pro.sky.examiner.service.QuestionService;
import pro.sky.examiner.domain.Question;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping(path="java")
public class JavaController {
    @Qualifier("javaQuestionService") QuestionService javaQuestionService;

    public JavaController(QuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @GetMapping("/add")
    public Question add(@RequestParam(required = false) String question,
                        @RequestParam(required = false) String answer) {
        return javaQuestionService.add(question, answer);
    }

    @GetMapping("/remove")
    public Question remove(@RequestParam(required = false) String question,
                           @RequestParam(required = false) String answer) {
        Question removeQuestion = new Question(question, answer);
        return javaQuestionService.remove(removeQuestion);
    }

    @GetMapping
    public Collection<Question> getAll() {
        return javaQuestionService.getAll();
    }
}
