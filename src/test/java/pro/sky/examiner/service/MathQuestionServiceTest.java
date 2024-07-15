package pro.sky.examiner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.examiner.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.sky.examiner.repository.MathQuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {
    @Mock
    MathQuestionRepository repositoryMock;
    @InjectMocks
    MathQuestionService sut;
    private static final String QUESTION_1 = "2 * 2?";
    private static final String ANSWER_1 = "4";
    private static final String QUESTION_2 = "7 - 5?";
    private static final String ANSWER_2 = "2";
    private static final String QUESTION_3 = "9 / 3?";
    private static final String ANSWER_3 = "3";
    @BeforeEach
    void init() {
        sut = new MathQuestionService(repositoryMock);
    }
    @Test
    void shouldReturnSameAddedQuestion() {
        Question expected = new Question(QUESTION_1, ANSWER_1);
        when(repositoryMock.add(new Question(QUESTION_1, ANSWER_1))).
                thenReturn(new Question(QUESTION_1, ANSWER_1));
        Question actual = sut.add(expected);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldReturnSameQuestion() {
        Question expected = new Question(QUESTION_1, ANSWER_1);
        when(repositoryMock.add(QUESTION_1, ANSWER_1)).
                thenReturn(new Question(QUESTION_1, ANSWER_1));
        Question actual = sut.add(QUESTION_1, ANSWER_1);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldReturnSameRemovedQuestion() {
        Question expected = new Question(QUESTION_1, ANSWER_1);
        when(repositoryMock.remove(new Question(QUESTION_1, ANSWER_1))).
                thenReturn(new Question(QUESTION_1, ANSWER_1));
        Question actual = sut.remove(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllQuestions() {
        List<Question> expected = new ArrayList<>(Arrays.asList(
                new Question(QUESTION_1, ANSWER_1),
                new Question(QUESTION_2, ANSWER_2),
                new Question(QUESTION_3, ANSWER_3)
        ));
        when(repositoryMock.getAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new Question(QUESTION_1, ANSWER_1),
                new Question(QUESTION_2, ANSWER_2),
                new Question(QUESTION_3, ANSWER_3)
        )));
        Collection<Question> actual = sut.getAll();
        Assertions.assertEquals(expected, actual);
    }
    public static Stream<Arguments> provideParamsForRandomQuestionTest() {
        return Stream.of(
                Arguments.of(QUESTION_1, ANSWER_1),
                Arguments.of(QUESTION_2, ANSWER_2),
                Arguments.of(QUESTION_3, ANSWER_3)
        );
    }
    @ParameterizedTest
    @MethodSource("provideParamsForRandomQuestionTest")
    void shouldReturnRandomQuestionFromPoolOfJavaQuestions(String questi0n, String answer) {
        when(repositoryMock.getAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new Question(QUESTION_1, ANSWER_1),
                new Question(QUESTION_2, ANSWER_2),
                new Question(QUESTION_3, ANSWER_3)
        )));
        when(repositoryMock.getRandomQuestion()).thenReturn(new Question(questi0n, answer));
        Assertions.assertTrue(sut.getAll().contains(sut.getRandomQuestion()));
    }
}