package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.TooBigAmountException;
import pro.sky.examiner.exception.TooSmallAmountException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService{
    private final Random random = new Random();
    private final List<QuestionService> services;
    public ExaminerServiceImpl(List<QuestionService> services) {
        this.services = services;
    }
    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new TooSmallAmountException("You must get at least one question");
        }
        int totalAmount;
        try {
            totalAmount = services.get(0).getAll().size();
        } catch (RuntimeException e) {
            totalAmount = services.get(1).getAll().size();
        }
        if (amount > totalAmount) {
            throw new TooBigAmountException("Try to get less questions, there are only "
                    + totalAmount);
        }
        Set<Question> randomQuestions = new HashSet<>();
        while (randomQuestions.size() < amount) {
            int randomService = random.nextInt(services.size());
            randomQuestions.add(services.get(randomService).getRandomQuestion());
        }
        return randomQuestions;
    }
}
