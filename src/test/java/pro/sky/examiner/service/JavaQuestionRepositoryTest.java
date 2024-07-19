package pro.sky.examiner.service;

import pro.sky.examiner.domain.Question;
import pro.sky.examiner.exception.NoArgumentException;
import pro.sky.examiner.exception.QuestionAlreadyExistsException;
import pro.sky.examiner.exception.QuestionNotExistException;
import pro.sky.examiner.exception.RepositoryIsEmptyException;
import pro.sky.examiner.repository.JavaQuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


public class JavaQuestionRepositoryTest {
    JavaQuestionRepository sut;
    String QUESTION_1 = "Что такое «переменная»?";
    String ANSWER_1 = "Это переменная";
    String QUESTION_2 = "По каким параметрам переменные различаются?";
    String ANSWER_2 = "Параметры";
    String QUESTION_3 = "Перечислите типы переменных и действия, которые с ними можно осуществлять.";
    String ANSWER_3 = "Типы и действия";
    String QUESTION_4 = "Новый вопрос";
    String ANSWER_4 = "Новый ответ";
    @BeforeEach
    void init() {
        sut = new JavaQuestionRepository();
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