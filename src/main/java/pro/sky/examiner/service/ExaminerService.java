package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
