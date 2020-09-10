package pl.idzikdev.quiz.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.idzikdev.quiz.domain.model.AnswerDto;
import pl.idzikdev.quiz.domain.model.QuestionDto;
import pl.idzikdev.quiz.repository.QuestionRepository;
import pl.idzikdev.quiz.service.QuestionService;

import java.util.Arrays;
import java.util.List;

@Component
public class QuestionRunner implements CommandLineRunner {
    @Autowired
    QuestionService questionService;

    @Override
    public void run(String... args) throws Exception {
        List<AnswerDto> list = Arrays.asList(
                new AnswerDto("sample answer1"),
                new AnswerDto("sample answer2"),
                new AnswerDto("sample answer3"));
        QuestionDto questionDto1=new QuestionDto();
        questionDto1.setAnswers(list);
        questionDto1.setName("Question1");
        questionDto1.setNumber(1L);
        QuestionDto questionDto2=new QuestionDto();
        questionDto2.setName("Question2");
        questionDto2.setNumber(2L);
        questionDto2.setAnswers(list);
        QuestionDto questionDto3=new QuestionDto();
        questionDto3.setName("Question3");
        questionDto3.setNumber(3L);
        questionDto3.setAnswers(list);
        QuestionDto questionDto4=new QuestionDto();
        questionDto4.setName("Question4");
        questionDto4.setNumber(4L);
        questionDto4.setAnswers(list);
        QuestionDto questionDto5=new QuestionDto();
        questionDto5.setName("Question5");
        questionDto5.setNumber(5L);
        questionDto5.setAnswers(list);
        QuestionDto questionDto6=new QuestionDto();
        questionDto6.setName("Question6");
        questionDto6.setNumber(6L);
        questionDto6.setAnswers(list);
        questionService.saveQuestion(questionDto1);
        questionService.saveQuestion(questionDto2);
        questionService.saveQuestion(questionDto3);
        questionService.saveQuestion(questionDto4);
        questionService.saveQuestion(questionDto5);
        questionService.saveQuestion(questionDto6);
    }
}
