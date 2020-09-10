package pl.idzikdev.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.idzikdev.quiz.converter.QuestionDtoToQuestionEntityConverter;
import pl.idzikdev.quiz.converter.QuestionEntityListToQuestionDtoListConverter;
import pl.idzikdev.quiz.converter.QuestionEntityToQuestionDtoconverter;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;
import pl.idzikdev.quiz.domain.model.QuestionDto;
import pl.idzikdev.quiz.repository.QuestionRepository;
import pl.idzikdev.quiz.service.QuestionService;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void saveQuestionEntity(QuestionEntity questionEntity) {
        questionRepository.save(questionEntity);
    }

    @Override
    public void saveQuestion(QuestionDto questionDto) {
        saveQuestionEntity(new QuestionDtoToQuestionEntityConverter().convert(questionDto));
    }

    @Override
    public List<QuestionDto> getQuestionsWithNumberGraterThen(Long number) {
        return new QuestionEntityListToQuestionDtoListConverter().convert(questionRepository.getQuestionsWithNumberGraterThen(number));
    }

    @Override
    public List<QuestionDto> getQuestionsByNameLike(String like) {
        return new QuestionEntityListToQuestionDtoListConverter().convert(questionRepository.getQuestionsByNameLike(like));
    }

    @Override
    public List<QuestionDto> findByNumberGreaterThanEqualOrderByNumberAsc(Long number) {
        return new QuestionEntityListToQuestionDtoListConverter().convert(questionRepository.findByNumberGreaterThanEqualOrderByNumberAsc(number));
    }

    @Override
    public Page<QuestionDto> findAll(Pageable pageable) {
        List<QuestionDto> questionDtoList=new QuestionEntityListToQuestionDtoListConverter().convert(questionRepository.findAll(pageable).getContent());
        return new PageImpl<>(questionDtoList,pageable,questionDtoList.size());
    }


}
