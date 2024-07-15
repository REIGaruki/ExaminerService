package pro.sky.examiner.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.repository.QuestionRepository;

import java.util.Collection;

@Service("mathQuestionService")
public class MathQuestionService implements QuestionService{
    private final QuestionRepository mathQuestions;

    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository mathQuestions) {
        this.mathQuestions = mathQuestions;
    }

    @Override
    public Question add(String question, String answer) {
        return mathQuestions.add(question, answer);
    }

    @Override
    public Question add(Question question) {
        return mathQuestions.add(question);
    }

    @Override
    public Question remove(Question question) {
        return mathQuestions.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return mathQuestions.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        return mathQuestions.getRandomQuestion();
    }
}
