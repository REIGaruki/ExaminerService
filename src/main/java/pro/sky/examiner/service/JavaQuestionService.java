package pro.sky.examiner.service;

import jakarta.annotation.PostConstruct;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.NoArgumentException;
import pro.sky.examiner.exception.QuestionAlreadyExistsException;
import pro.sky.examiner.exception.QuestionNotExistException;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("javaQuestionService")
public class JavaQuestionService implements QuestionService{
    private List<Question> javaQuestions;

    public JavaQuestionService(List<Question> javaQuestions) {
        this.javaQuestions = javaQuestions;
    }

    @PostConstruct
    public void init() {
        this.javaQuestions = new ArrayList<>(Arrays.asList(
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
        ));

    }

    @Override
    public Question add(String question, String answer) {
        if (question == null || answer == null || question.equals("") || answer.equals("")) {
            throw new NoArgumentException("Absence of question or answer");
        } else {
            Question newQuestion = new Question(question, answer);
            add(newQuestion);
            return newQuestion;
        }
    }

    @Override
    public Question add(Question question) {
        if (getAll().contains(question)) {
            throw new QuestionAlreadyExistsException("Question already exists");
        } else {
            javaQuestions.add(question);
            return question;
        }
    }

    @Override
    public Question remove(Question question) {
        if (!getAll().contains(question)) {
            throw new QuestionNotExistException("Question does not exist");
        } else {
            javaQuestions.remove(question);
            return question;
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
        Random random = new Random();
        int randomId = random.nextInt(javaQuestions.size());
        return javaQuestions.get(randomId);
    }
}
