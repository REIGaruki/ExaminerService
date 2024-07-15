package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.exception.TooBigAmountException;
import pro.sky.examiner.exception.TooSmallAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    private final Question QUESTION_1 = new Question("Что?",
            "Это"
    );
    private final Question QUESTION_2 = new Question("Где?",
            "Здесь"
    );
    private final Question QUESTION_3 = new Question("Когда?",
            "Сейчас");
    private final Question QUESTION_4 = new Question("Q4?",
            "A4"
    );
    private final Question QUESTION_5 = new Question("Q5?",
            "A5"
    );
    private final Question QUESTION_6 = new Question("Q6?",
            "A6");
    private final Question QUESTION_7 = new Question("Q7?",
            "A7");
    private final int JAVA_AMOUNT = 4;
    private final int MATH_AMOUNT = 3;
    private final int TOTAL_AMOUNT = MATH_AMOUNT + JAVA_AMOUNT;
    private final int ERROR_AMOUNT = TOTAL_AMOUNT + 1;
    private final Random random = new Random();

    @Mock
    QuestionService javaQuestionServiceMock;
    @Mock
    QuestionService mathQuestionServiceMock;
    ExaminerServiceImpl sut;

    @BeforeEach
    void initSut() {
        sut = new ExaminerServiceImpl(javaQuestionServiceMock, mathQuestionServiceMock);
        List<Question> javaQuestions = new ArrayList<>();
        javaQuestions.add(QUESTION_1);
        javaQuestions.add(QUESTION_2);
        javaQuestions.add(QUESTION_3);
        javaQuestions.add(QUESTION_4);
        List<Question> mathQuestions = new ArrayList<>();
        mathQuestions.add(QUESTION_5);
        mathQuestions.add(QUESTION_6);
        mathQuestions.add(QUESTION_7);
        when(javaQuestionServiceMock.getAll()).thenReturn(javaQuestions);
        when(mathQuestionServiceMock.getAll()).thenReturn(mathQuestions);
    }

    @Test
    void shouldThrowExceptionWhenAmountOfRandomQuestionsIsNegativeOrZero() {
        int randomNegativeNumberOrZero = random.nextInt(Integer.MAX_VALUE) * (-1);
        Assertions.assertThrows(TooSmallAmountException.class,
                () -> sut.getQuestions(randomNegativeNumberOrZero));
    }
    @Test
    void shouldThrowExceptionWhenAmountOfRandomQuestionsIsGreaterThanQuestionCollectionSize() {
        Assertions.assertThrows(TooBigAmountException.class, () -> sut.getQuestions(ERROR_AMOUNT));
    }
    @Test
    void shouldThrowExceptionWhenThereAreNoQuestions() {
        when(javaQuestionServiceMock.getAll()).thenReturn(new ArrayList<>());
        when(mathQuestionServiceMock.getAll()).thenReturn(new ArrayList<>());
        int randomPositiveNumber = random.nextInt(Integer.MAX_VALUE) + 1;
        Assertions.assertThrows(RepositoryIsEmptyException.class,
                () -> sut.getQuestions(randomPositiveNumber));
    }
    @Test
    void shouldReturnAmountOfUniqueQuestions() {
        when(javaQuestionServiceMock.getRandomQuestion()).thenReturn(
                QUESTION_1,
                QUESTION_1,
                QUESTION_2,
                QUESTION_3,
                QUESTION_3,
                QUESTION_1,
                QUESTION_2,
                QUESTION_4
        );
        when(mathQuestionServiceMock.getRandomQuestion()).thenReturn(
                QUESTION_5,
                QUESTION_6,
                QUESTION_6,
                QUESTION_5,
                QUESTION_7
        );
        Set<Question> expected = new HashSet<>();
        expected.add(QUESTION_1);
        expected.add(QUESTION_2);
        expected.add(QUESTION_3);
        expected.add(QUESTION_4);
        expected.add(QUESTION_5);
        expected.add(QUESTION_6);
        expected.add(QUESTION_7);
        Collection<Question> actual = sut.getQuestions(TOTAL_AMOUNT);
        Assertions.assertEquals(expected, actual);
    }

}