package pl.idzikdev.quiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.idzikdev.quiz.domain.entity.QuestionEntity;
import pl.idzikdev.quiz.domain.model.QuestionDto;
import pl.idzikdev.quiz.service.QuestionService;

import java.util.List;

@RestController()
@RequestMapping("/api/v2/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

//    @GetMapping(params = "number")
//    public List<QuestionDto> getQuestionDtos(@RequestParam(name = "number") Long nr) {
//        return questionService.getQuestionsWithNumberGraterThen(nr);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@RequestBody QuestionDto questionDto) {
        questionService.saveQuestion(questionDto);
    }

//    @GetMapping(params = "like")
//    public List<QuestionDto> getQuestionsByNameLike(@RequestParam(name = "like") String like){
//        return questionService.getQuestionsByNameLike(like);
//    }

//    @GetMapping(params = "number")
//    public List<QuestionDto> getQuestionsByGreaterThanEqual(@RequestParam(name = "number") Long number){
//        return questionService.findByNumberGreaterThanEqualOrderByNumberAsc(number);
//    }

    @GetMapping(params = "page")
    public List<QuestionDto> getQuestionsByPage(@RequestParam(name = "page") Integer number){
        Pageable pageable= PageRequest.of(number,2);
        return questionService.findAll(pageable).getContent();
    }
}
