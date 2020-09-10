package pl.idzikdev.quiz.converter;

import org.springframework.core.convert.converter.Converter;
import pl.idzikdev.quiz.domain.entity.AnswerEntity;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;
import pl.idzikdev.quiz.domain.model.AnswerDto;
import pl.idzikdev.quiz.domain.model.QuestionDto;

import java.util.ArrayList;
import java.util.List;

public class QuestionDtoToQuestionEntityConverter implements Converter<QuestionDto, QuestionEntity> {
    @Override
    public QuestionEntity convert(QuestionDto questionDto) {
        QuestionEntity questionEntity=new QuestionEntity();
        questionEntity.setNumber(questionDto.getNumber());
        questionEntity.setName(questionDto.getName());
        List<AnswerDto> answerDtoList = questionDto.getAnswers();
        List<AnswerEntity> answerEntityList=new ArrayList<>();
        for (AnswerDto answerDTO : answerDtoList
        ) {
            AnswerEntity answerEntity=new AnswerEntity();
            answerEntity.setName(answerDTO.getName());
            answerEntityList.add(answerEntity);
        }
        questionEntity.setAnswerEntities(answerEntityList);
        return questionEntity;
    }
}
