package pro.sky.examiner.service;

import org.springframework.beans.factory.annotation.Qualifier;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.exception.TooBigAmountException;
import pro.sky.examiner.exception.TooSmallAmountException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService{
    private final QuestionService javaService;
    private final QuestionService mathService;
    private final Random random = new Random();

    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService javaService,
                               @Qualifier("mathQuestionService") QuestionService mathService) {
        this.javaService = javaService;
        this.mathService = mathService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new TooSmallAmountException("You must get at least one question");
        }
        if (javaService.getAll().isEmpty() && mathService.getAll().isEmpty()) {
            throw new RepositoryIsEmptyException("We have no questions for you yet");
        }
        if (amount > (javaService.getAll().size() + mathService.getAll().size())) {
            throw new TooBigAmountException("Try to get less questions, there are only "
                    + (javaService.getAll().size() + mathService.getAll().size()));
        }
        Set<Question> randomQuestions = new HashSet<>();
        int javaAmount = 0;
        int mathAmount = 0;
        while (javaAmount + mathAmount != amount) {
            javaAmount = random.nextInt(javaService.getAll().size() + 1);
            mathAmount = random.nextInt(mathService.getAll().size() + 1);
        }
        while (randomQuestions.size() < javaAmount) {
            randomQuestions.add(javaService.getRandomQuestion());
        }
        while (randomQuestions.size() < amount) {
            randomQuestions.add(mathService.getRandomQuestion());
        }
        return randomQuestions;
    }

}
