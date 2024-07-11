package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.exception.TooBigAmountException;
import pro.sky.examiner.exception.TooSmallAmountException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService{
    private final QuestionService javaService;

    public ExaminerServiceImpl(QuestionService javaService) {
        this.javaService = javaService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new TooSmallAmountException("You must get at least one question");
        }
        if (javaService.getAll().isEmpty()) {
            throw new RepositoryIsEmptyException("We have no questions for you yet");
        }
        int totalAmount = javaService.getAll().size();
        if (amount > totalAmount) {
            throw new TooBigAmountException("Try to get less questions, there are only "
                    + totalAmount);
        }
        Set<Question> randomQuestions = new HashSet<>();
        while (randomQuestions.size() < amount) {
            randomQuestions.add(javaService.getRandomQuestion());
        }
        return randomQuestions;
    }

}
