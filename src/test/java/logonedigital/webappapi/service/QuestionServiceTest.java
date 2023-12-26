package logonedigital.webappapi.service;

import logonedigital.webappapi.entity.Question;
import logonedigital.webappapi.entity.TypeQuestion;
import logonedigital.webappapi.repository.QuestionRepository;
import logonedigital.webappapi.service.quizz.QuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class QuestionServiceTest {
    @Test
    public void createQuestionShouldReturnTheQuestion() {
        // Arranger
        QuestionRepository questionRepository = Mockito.mock(QuestionRepository.class);
        QuestionService questionService = new QuestionService(questionRepository);
        Question question = new Question(0,"What is 1+1 ?",
                "Math",
                TypeQuestion.QCM,
                Arrays.asList("2","5","3"),
                Arrays.asList("2"));
        // En action
        questionService.createQuestion(question);

        // Assertion
        Mockito.verify(questionRepository).save(question);
    }
}
