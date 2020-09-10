package pl.idzikdev.quiz.controllers;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.idzikdev.quiz.domain.model.AnswerDto;
import pl.idzikdev.quiz.domain.model.QuestionDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController()
@RequestMapping("/api/v1/questions")
public class QuestionsController {

    private List<QuestionDto> questionDtos = new ArrayList<>();

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<QuestionDto>> getQuestionDtos() {
        return ResponseEntity.ok().header("Cache-Control", "max-age" + "=3600").body(questionDtos);
    }


    @GetMapping(params = {"name", "number"})
    public ResponseEntity<Resources<Resource<QuestionDto>>> getQuestionsByNameAndNumber(@RequestParam String name,
                                                                                        @RequestParam Long number) {
        Resources<Resource<QuestionDto>> resources = new Resources<>
                (questionDtos.stream()
                        .filter(q -> q.getNumber() == number)
                        .filter(q -> name.equals(q.getName()))
                        .map(q -> mapToResource(q))
                        .collect(Collectors.toList())
                );
        addQuestionsLink(resources,"allDocs");
        addFindQuestionsLink(resources,"self",name,number);
        return ResponseEntity.ok()
                .header("Cache-Control", "max-age" + "=3600")
                .body(resources);

    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getQuestionsNames() {
        return questionDtos.stream()
                .map(q -> q.getName())
                .reduce((s, next) -> String.join(",", s, next))
                .orElse("");
    }

    @GetMapping("/{number}")
    public ResponseEntity<Resource<QuestionDto>> getQuestion(@PathVariable Long number) {
        return new ResponseEntity<>(mapToResource(findQuestionByNumber(number).get()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Resources<Resource<QuestionDto>>> getAllQuestions() {
        Resources<Resource<QuestionDto>> resources = new Resources<>(
                questionDtos.stream().map(q -> mapToResource(q)).collect(Collectors.toList())
        );
        addQuestionsLink(resources, "self");
        addFindQuestionsLink(resources, "questionsByNameAndNumber", null, null);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @GetMapping("/{number}/name")
    public Optional<String> getNameOfQuestion(@PathVariable Long number) {
        return findQuestionByNumber(number)
                .map(QuestionDto::getName);
    }

    @GetMapping(params = "name")
    public Stream<QuestionDto> findQuestionByName(@RequestParam String name) {
        return questionDtos.stream().filter(q -> name.equals(q.getName()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@RequestBody QuestionDto questionDto) {
        questionDtos.add(questionDto);
    }

    @PostMapping(value = "/{number}/answers")
    public void addAnswer(@PathVariable Long number,
                          @RequestBody AnswerDto answerDTO) {
        questionDtos.stream().filter(q -> q.getNumber() == number)
                .findAny()
                .ifPresent(q -> q.getAnswers().add(answerDTO));
    }


    @PutMapping(value = "/{number}")
    private void replaceQuestion(@PathVariable Long number,
                                 @RequestBody QuestionDto questionDto) {
        findQuestionByNumber(number)
                .ifPresent(q -> {
                    q.setName(questionDto.getName());
                    q.setAnswers(questionDto.getAnswers());
                });
    }

    @PatchMapping(value = "/{number}")
    private void updateQuestion(@PathVariable Long number,
                                @RequestBody QuestionDto questionDto) {
        findQuestionByNumber(number)
                .ifPresent(q -> {
                    if (questionDto.getName() != null)
                        q.setName(questionDto.getName());
                    if (questionDto.getAnswers() != null)
                        q.setAnswers(questionDto.getAnswers());
                });
    }

    @DeleteMapping(value = "/{number}")
    private ResponseEntity<Object> removeQuestion(@PathVariable Long number) {
        boolean removed = questionDtos.removeIf(q -> q.getNumber() == number);
        if (removed) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    private Optional<QuestionDto> findQuestionByNumber(Long number) {
        return questionDtos.stream()
                .filter(q -> q.getNumber() == number)
                .findAny();
    }

    private Resource<QuestionDto> mapToResource(QuestionDto questionDto) {
        Link link = ControllerLinkBuilder.linkTo(QuestionsController.class).slash(questionDto.getNumber()).withSelfRel();
        Resource<QuestionDto> questionResource = new Resource<>(findQuestionByNumber(questionDto.getNumber()).get(), link);
        return questionResource;
    }

    private void addFindQuestionsLink(Resources<Resource<QuestionDto>> resources, String rel, String name, Long number) {
        resources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(QuestionsController.class)
                .getQuestionsByNameAndNumber(name, number))
                .withRel(rel));
    }

    private void addQuestionsLink(Resources<Resource<QuestionDto>> resources, String rel) {
        resources.add(ControllerLinkBuilder.linkTo(QuestionsController.class).withRel(rel));
    }
}
