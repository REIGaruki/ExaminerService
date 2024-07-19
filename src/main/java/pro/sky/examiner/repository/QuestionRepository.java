package pro.sky.examiner.repository;

import pro.sky.examiner.domain.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question add(String question, String answer);
    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
    Question getRandomQuestion();
}
