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
    private final int JAVA_AMOUNT = 4;
    private final int ERROR_AMOUNT = JAVA_AMOUNT + 1;
    private final Random random = new Random();

    @Mock
    QuestionService javaQuestionServiceMock;
    @Mock
    QuestionService mathQuestionServiceMock;
    ExaminerServiceImpl sut;
    private List<Question> javaQuestions;
    @BeforeEach
    void initSut() {
        List<QuestionService> services = new ArrayList<>(List.of(javaQuestionServiceMock,mathQuestionServiceMock));
        sut = new ExaminerServiceImpl(services);
        javaQuestions = new ArrayList<>();
        javaQuestions.add(QUESTION_1);
        javaQuestions.add(QUESTION_2);
        javaQuestions.add(QUESTION_3);
        javaQuestions.add(QUESTION_4);
    }

    @Test
    void shouldThrowExceptionWhenAmountOfRandomQuestionsIsNegativeOrZero() {
        int randomNegativeNumberOrZero = random.nextInt(Integer.MAX_VALUE) * (-1);
        Assertions.assertThrows(TooSmallAmountException.class,
                () -> sut.getQuestions(randomNegativeNumberOrZero));
    }
    @Test
    void shouldThrowExceptionWhenAmountOfRandomQuestionsIsGreaterThanQuestionCollectionSize() {
        when(javaQuestionServiceMock.getAll()).thenReturn(javaQuestions);
        Assertions.assertThrows(TooBigAmountException.class, () -> sut.getQuestions(ERROR_AMOUNT));
    }
    @Test
    void shouldReturnAmountOfUniqueQuestions() {
        when(javaQuestionServiceMock.getAll()).thenReturn(javaQuestions);
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
                QUESTION_1,
                QUESTION_2,
                QUESTION_3,
                QUESTION_4
        );
        Set<Question> expected = new HashSet<>();
        expected.add(QUESTION_1);
        expected.add(QUESTION_2);
        expected.add(QUESTION_3);
        expected.add(QUESTION_4);
        Collection<Question> actual = sut.getQuestions(JAVA_AMOUNT);
        Assertions.assertEquals(expected, actual);
    }

}