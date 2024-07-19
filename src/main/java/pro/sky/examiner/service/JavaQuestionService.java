package pro.sky.examiner.service;

import org.springframework.beans.factory.annotation.Qualifier;
import pro.sky.examiner.domain.Question;
import org.springframework.stereotype.Service;
import pro.sky.examiner.repository.QuestionRepository;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService{
    private final QuestionRepository javaQuestions;

    public JavaQuestionService(@Qualifier("javaQuestionRepository") QuestionRepository javaQuestions) {
        this.javaQuestions = javaQuestions;
    }

    @Override
    public Question add(String question, String answer) {
        return javaQuestions.add(question, answer);
    }

    @Override
    public Question add(Question question) {
        return javaQuestions.add(question);
    }

    @Override
    public Question remove(Question question) {
        return javaQuestions.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return javaQuestions.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        return javaQuestions.getRandomQuestion();
    }
}
