package pro.sky.examiner.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.examiner.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.sky.examiner.exception.NoRepositoryException;


@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {
    QuestionService sut = new MathQuestionService();
    private final Question QUESTION_1 = new Question("Что?",
            "Это"
    );
    @Test
    void shouldThrowExceptionWhenAddRemoveOrGetAll() {
        Assertions.assertThrows(NoRepositoryException.class, () -> sut.add(QUESTION_1));
        Assertions.assertThrows(NoRepositoryException.class, () -> sut.remove(QUESTION_1));
        Assertions.assertThrows(NoRepositoryException.class, () -> sut.getAll());
    }
}