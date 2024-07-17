package pro.sky.examiner.repository;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.NoArgumentException;
import pro.sky.examiner.exception.QuestionAlreadyExistsException;
import pro.sky.examiner.exception.QuestionNotExistException;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JavaQuestionRepository implements QuestionRepository{
    private final List<Question> javaQuestions = new ArrayList<>();
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        this.javaQuestions.addAll(new ArrayList<>(Arrays.asList(
                new Question("Что такое «переменная»?",
                        "Это переменная"),
                new Question("По каким параметрам переменные различаются?",
                        "Параметры"),
                new Question("Перечислите типы переменных и действия, которые с ними можно осуществлять.",
                        "Типы и действия"),
                new Question("Что означает \"инициализация\"?",
                        "Означает инициалтзацию"),
                new Question("Какие особенности инициализации вы можете назвать?",
                        "Никакие"),
                new Question("Какие условные операторы вы знаете? Дайте краткое определение каждому из них.",
                        "Краткие определения"),
                new Question("Что такое \"цикл\"?",
                        "Это цикл"),
                new Question("Ка. они разныекие циклы вы знаете, в чем их отличия?",
                        "Знаю все"),
                new Question("Что вы знаете о массивах?",
                        "ЧТо они масивные"),
                new Question("Что такое \"метод\"?",
                        "Сериал")
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
        javaQuestions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        checkIfQuestionOrAnswerIsEmpty(question);
        if (!getAll().contains(question)) {
            throw new QuestionNotExistException("Question does not exist");
        }
        javaQuestions.remove(question);
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
        return this.javaQuestions;
    }

    @Override
    public Question getRandomQuestion() {
        if (getAll().isEmpty()) {
            throw new RepositoryIsEmptyException("There are no questions for you yet");
        }
        int randomId = random.nextInt(javaQuestions.size());
        return javaQuestions.get(randomId);
    }
}
