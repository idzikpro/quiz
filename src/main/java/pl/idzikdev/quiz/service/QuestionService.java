package pl.idzikdev.quiz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;
import pl.idzikdev.quiz.domain.model.QuestionDto;

import java.util.List;

public interface QuestionService {
    public void saveQuestionEntity(QuestionEntity questionEntity);
    public void saveQuestion(QuestionDto questionDto);
    public List<QuestionDto> getQuestionsWithNumberGraterThen(Long number);
    public List<QuestionDto> getQuestionsByNameLike(String like);
    public List<QuestionDto> findByNumberGreaterThanEqualOrderByNumberAsc(Long number);
    public Page<QuestionDto> findAll(Pageable pageable);
}
