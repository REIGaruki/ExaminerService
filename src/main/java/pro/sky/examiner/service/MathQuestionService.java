package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.NoRepositoryException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;

@Service
public class MathQuestionService implements QuestionService{
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        throw new NoRepositoryException("Method not allowed");
    }

    @Override
    public Question add(Question question) {
        throw new NoRepositoryException("Method not allowed");
    }

    @Override
    public Question remove(Question question) {
        throw new NoRepositoryException("Method not allowed");
    }

    @Override
    public Collection<Question> getAll() {
        throw new NoRepositoryException("Method not allowed");
    }

    @Override
    public Question getRandomQuestion() {
        char[] maths = {'+', '-', '*', '/'};
        int a = random.nextInt(1000);
        int b = random.nextInt(1000);
        int c = random.nextInt(4);
        int d = switch (c) {
            case 0 -> a + b;
            case 1 -> a - b;
            case 2 -> a * b;
            case 3 -> {
                while (b == 0) {
                    b = random.nextInt(1000);
                }
                yield a / b;
            }
            default -> 0;
        };
        return new Question(String.valueOf(a) + ' ' + maths[c] + ' ' + String.valueOf(b) + " = ?"
                , String.valueOf(d));
    }
}