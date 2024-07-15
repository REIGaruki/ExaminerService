package pro.sky.examiner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.service.QuestionService;

import java.util.Collection;


@RestController
@RequestMapping(path="math")
public class MathController {
    private final QuestionService mathQuestionService;

    public MathController(QuestionService mathQuestionService) {
        this.mathQuestionService = mathQuestionService;
    }

    @GetMapping("/add")
    public Question add(@RequestParam(required = false) String question,
                        @RequestParam(required = false) String answer) {
        return mathQuestionService.add(question, answer);
    }

    @GetMapping("/remove")
    public Question remove(@RequestParam(required = false) String question,
                           @RequestParam(required = false) String answer) {
        return mathQuestionService.remove(new Question(question, answer));
    }

    @GetMapping
    public Collection<Question> getAll() {
        return mathQuestionService.getAll();
    }
}

