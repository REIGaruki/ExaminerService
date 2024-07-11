package pro.sky.examiner.service;

import org.mockito.InjectMocks;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.exception.TooBigAmountException;
import pro.sky.examiner.exception.TooSmallAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    private final Random random = new Random();
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
    private List<Question> JAVA_QUESTION_LIST = new ArrayList<>();

    @Mock
    JavaQuestionService javaQuestionServiceMock;
    @InjectMocks
    ExaminerServiceImpl sut;

    @BeforeEach
    void initSut() {
        JAVA_QUESTION_LIST.add(QUESTION_1);
        JAVA_QUESTION_LIST.add(QUESTION_2);
        JAVA_QUESTION_LIST.add(QUESTION_3);
        JAVA_QUESTION_LIST.add(QUESTION_4);
    }

    @Test
    @DisplayName("")
    void shouldThrowExceptionWhenAmountOfRandomQuestionsIsGreaterThanQuestionCollectionSizeOrNegativeOrZero() {
        int randomNegativeNumberOrZero = random.nextInt(Integer.MAX_VALUE) * (-1);
        when(javaQuestionServiceMock.getAll()).thenReturn(JAVA_QUESTION_LIST);
        Assertions.assertThrows(TooBigAmountException.class, () -> sut.getQuestions(ERROR_AMOUNT));
        Assertions.assertThrows(TooSmallAmountException.class,
                () -> sut.getQuestions(randomNegativeNumberOrZero));
    }
    @Test
    void shouldThrowExceptionWhenThereAreNoQuestions() {
        when(javaQuestionServiceMock.getAll()).thenReturn(new ArrayList<>());
        int randomPositiveNumber = random.nextInt(Integer.MAX_VALUE) + 1;
        Assertions.assertThrows(RepositoryIsEmptyException.class,
                () -> sut.getQuestions(randomPositiveNumber));
    }
    @Test
    void shouldReturnAmountOfUniqueQuestions() {
        when(javaQuestionServiceMock.getRandomQuestion()).thenReturn(
                QUESTION_1,
                QUESTION_2,
                QUESTION_3,
                QUESTION_4
        );
        when(javaQuestionServiceMock.getAll()).thenReturn(JAVA_QUESTION_LIST);
        Set<Question> expected = new HashSet<>();
        expected.add(QUESTION_1);
        expected.add(QUESTION_2);
        expected.add(QUESTION_3);
        expected.add(QUESTION_4);
        Collection<Question> actual = sut.getQuestions(JAVA_AMOUNT);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(JAVA_AMOUNT, actual.size());
    }

}