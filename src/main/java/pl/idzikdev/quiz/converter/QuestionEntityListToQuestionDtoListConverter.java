package pl.idzikdev.quiz.converter;

import org.springframework.core.convert.converter.Converter;
import pl.idzikdev.quiz.domain.entity.AnswerEntity;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;
import pl.idzikdev.quiz.domain.model.AnswerDto;
import pl.idzikdev.quiz.domain.model.QuestionDto;

import java.util.ArrayList;
import java.util.List;

public class QuestionEntityListToQuestionDtoListConverter implements Converter<List<QuestionEntity>,List<QuestionDto>> {
    @Override
    public List<QuestionDto> convert(List<QuestionEntity> questionEntities) {
        List<QuestionDto> list=new ArrayList<>();
        for (QuestionEntity entity:questionEntities
             ) {
            QuestionDto dto=new QuestionDto();
            dto.setNumber(entity.getNumber());
            dto.setName(entity.getName());
            List<AnswerEntity> answerEntityList=entity.getAnswerEntities();
            List<AnswerDto> answerDtoList=new ArrayList<>();
            for (AnswerEntity answerEntity:answerEntityList
                 ) {
                AnswerDto answerDto=new AnswerDto();
                answerDto.setName(answerEntity.getName());
                answerDtoList.add(answerDto);
            }
            dto.setAnswers(answerDtoList);
            list.add(dto);
        }
        return list;
    }
}
