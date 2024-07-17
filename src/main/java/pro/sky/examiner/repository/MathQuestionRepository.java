package pro.sky.examiner.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.NoArgumentException;
import pro.sky.examiner.exception.QuestionAlreadyExistsException;
import pro.sky.examiner.exception.QuestionNotExistException;
import pro.sky.examiner.exception.RepositoryIsEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Repository
public class MathQuestionRepository implements QuestionRepository{
    private final List<Question> mathQuestions = new ArrayList<>();
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        this.mathQuestions.addAll(new ArrayList<>(Arrays.asList(
                new Question("2 * 2?",
                        "4"),
                new Question("99 + 1?",
                        "100"),
                new Question("500 / 4?",
                        "125"),
                new Question("134 - 23?",
                        "111"),
                new Question("17 * 3?",
                        "51"),
                new Question("1024 / 8?",
                        "128"),
                new Question("715 - 346?",
                        "369"),
                new Question("77 + 777?",
                        "854"),
                new Question("100 - 1000?",
                        "-900"),
                new Question("19456 * 0?",
                        "0")
        )));
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        checkIfQuestionOrAnswerIsEmpty(question);
        if (getAll().contains(question)) {
            throw new QuestionAlreadyExistsException("Question already exists");
        }
        mathQuestions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        checkIfQuestionOrAnswerIsEmpty(question);
        if (!getAll().contains(question)) {
            throw new QuestionNotExistException("Question does not exist");
        }
        mathQuestions.remove(question);
        return question;
    }
    private void checkIfQuestionOrAnswerIsEmpty(Question question) {
        if (question.getQuestion() == null || question.getAnswer() == null ||
                question.getAnswer().isEmpty() || question.getQuestion().isEmpty()) {
            throw new NoArgumentException("Absence of question or answer");
        }
    }

    @Override
    public List<Question> getAll() {
        return this.mathQuestions;
    }

    @Override
    public Question getRandomQuestion() {
        if (getAll().isEmpty()) {
            throw new RepositoryIsEmptyException("There are no questions for you yet");
        }
        int randomId = random.nextInt(mathQuestions.size());
        return mathQuestions.get(randomId);
    }
}
