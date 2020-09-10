package pl.idzikdev.quiz.converter;

import org.springframework.core.convert.converter.Converter;
import pl.idzikdev.quiz.domain.entity.AnswerEntity;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;
import pl.idzikdev.quiz.domain.model.AnswerDto;
import pl.idzikdev.quiz.domain.model.QuestionDto;

import java.util.ArrayList;
import java.util.List;

public class QuestionEntityToQuestionDtoconverter implements Converter<QuestionEntity, QuestionDto> {
    @Override
    public QuestionDto convert(QuestionEntity questionEntity) {
        QuestionDto questionDto=new QuestionDto();
        questionDto.setNumber(questionEntity.getNumber());
        questionDto.setName(questionEntity.getName());
        List<AnswerEntity> answerEntities = questionEntity.getAnswerEntities();
        List<AnswerDto> answerDtoList=new ArrayList<>();
        for (AnswerEntity answerEntity : answerEntities
        ) {
            AnswerDto answerDto=new AnswerDto();
            answerDto.setName(answerEntity.getName());
            answerDtoList.add(answerDto);
        }
        questionDto.setAnswers(answerDtoList);
        return questionDto;
    }
}
