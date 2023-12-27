package logonedigital.webappapi.service.quizz;

import logonedigital.webappapi.entity.Question;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Integer id);
    List<String> getAllSubjects();
    Question updateQuestion(Integer id, Question question) throws ChangeSetPersister.NotFoundException;
    void  deleteQuestion(Integer id);
    List<Question> getQuestionsForUser(Integer numOfQuestions, String subject);
}
