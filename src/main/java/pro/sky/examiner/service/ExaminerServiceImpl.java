package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.exception.TooBigAmountException;
import pro.sky.examiner.exception.TooSmallAmountException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService{
    Random random = new Random();
    private final List<QuestionService> services;
    public ExaminerServiceImpl(List<QuestionService> services) {
        this.services = services;
    }
    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0) {
            throw new TooSmallAmountException("You must get at least one question");
        }
        if (services.get(0).getAll().isEmpty()) {
            throw new RepositoryIsEmptyException("We have no questions for you yet");
        }
        int totalAmount = services.get(0).getAll().size();
        if (amount > totalAmount) {
            throw new TooBigAmountException("Try to get less questions, there are only "
                    + totalAmount);
        }
        int javaQuestionAmount;
        Set<Question> randomQuestions = new HashSet<>();
        javaQuestionAmount = random.nextInt(amount);
        while (randomQuestions.size() < javaQuestionAmount) {
            randomQuestions.add(services.get(0).getRandomQuestion());
        }
        while (randomQuestions.size() < amount) {
            randomQuestions.add(services.get(1).getRandomQuestion());
        }
        return randomQuestions;
    }

}
