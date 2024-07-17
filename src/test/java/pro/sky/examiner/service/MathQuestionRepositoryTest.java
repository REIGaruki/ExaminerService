package pro.sky.examiner.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.NoArgumentException;
import pro.sky.examiner.exception.QuestionAlreadyExistsException;
import pro.sky.examiner.exception.QuestionNotExistException;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.repository.MathQuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


public class MathQuestionRepositoryTest {
    MathQuestionRepository sut;
    private final String QUESTION_1 = "2 * 2?";
    private final String ANSWER_1 = "4";
    private final String QUESTION_2 = "7 - 5?";
    private final String ANSWER_2 = "2";
    private final String QUESTION_3 = "9 / 3?";
    private final String ANSWER_3 = "3";
    private final String QUESTION_4 = "100 + 1?";
    private final String ANSWER_4 = "101";
    @BeforeEach
    void init() {
        sut = new MathQuestionRepository();
        sut.add(new Question(QUESTION_1, ANSWER_1));
        sut.add(new Question(QUESTION_2, ANSWER_2));
        sut.add(new Question(QUESTION_3, ANSWER_3));
    }

    public static Stream<Arguments> provideParamsForAddQuestionTest() {
        return Stream.of(
                Arguments.of(null, "Answer"),
                Arguments.of("","Answer"),
                Arguments.of("Question", null),
                Arguments.of("Question", "")
        );
    }
    @ParameterizedTest
    @MethodSource("provideParamsForAddQuestionTest")
    void shouldThrowExceptionWhenThereAreNoQuestionOrAnswer(String questi0n, String answer) {
        Assertions.assertThrows(NoArgumentException.class, () -> sut.add(questi0n, answer));
        Assertions.assertThrows(NoArgumentException.class, () -> sut.remove(new Question(questi0n, answer)));
    }
    @Test
    void shouldAddQuestionCorrectly() {
        Question newQuestion = new Question(QUESTION_4, ANSWER_4);
        List<Question> expected = new ArrayList<>(Arrays.asList(
                new Question(QUESTION_1, ANSWER_1),
                new Question(QUESTION_2, ANSWER_2),
                new Question(QUESTION_3, ANSWER_3),
                new Question(QUESTION_4, ANSWER_4)
        ));
        sut.add(newQuestion);
        Collection<Question> actual = sut.getAll();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldReturnSameAddedQuestion() {
        Question expected = new Question(QUESTION_4, ANSWER_4);
        Question actual = sut.add(expected);
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(sut.getAll().contains(expected));
    }
    @Test
    void shouldThrowExceptionIfQuestionIsAlreadyAdded() {
        Assertions.assertThrows(QuestionAlreadyExistsException.class,
                () -> sut.add(new Question(QUESTION_1, ANSWER_1)
                ));
    }

    @Test
    void shouldRemoveQuestionCorrectly() {
        Question removedQuestion = new Question(QUESTION_2, ANSWER_2);
        List<Question> expected = new ArrayList<>(Arrays.asList(
                new Question(QUESTION_1, ANSWER_1),
                new Question(QUESTION_3, ANSWER_3)
        ));
        sut.remove(removedQuestion);
        Collection<Question> actual = sut.getAll();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void shouldReturnSameRemovedQuestion() {
        Question expected = new Question(QUESTION_1, ANSWER_1);
        Question actual = sut.remove(expected);
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(sut.getAll().contains(expected));
    }
    @Test
    void shouldThrowExceptionWhenRemoveQuestionThatDoesNotExist() {
        Question removedQuestion = new Question(QUESTION_4, QUESTION_4);
        Assertions.assertThrows(QuestionNotExistException.class, () -> sut.remove(removedQuestion));
    }

    @Test
    void shouldGetAllQuestions() {
        List<Question> expected = new ArrayList<>(Arrays.asList(
                new Question(QUESTION_1, ANSWER_1),
                new Question(QUESTION_2, ANSWER_2),
                new Question(QUESTION_3, ANSWER_3)
        ));
        Collection<Question> actual = sut.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnRandomQuestionFromPoolOfJavaQuestions() {
        Question randomQuestion = sut.getRandomQuestion();
        Assertions.assertTrue(sut.getAll().contains(randomQuestion));
    }

    @Test
    void shouldThrowExceptionWhenRepositoryIsEmpty() {
        sut.remove(new Question(QUESTION_1, ANSWER_1));
        sut.remove(new Question(QUESTION_2, ANSWER_2));
        sut.remove(new Question(QUESTION_3, ANSWER_3));
        Assertions.assertThrows(RepositoryIsEmptyException.class, ()-> sut.getRandomQuestion());
    }

}