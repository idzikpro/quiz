package pl.idzikdev.quiz.controllers;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.idzikdev.quiz.domain.model.Answer;
import pl.idzikdev.quiz.domain.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController()
@RequestMapping("/api/v1/questions")
public class QuestionsController {

    private List<Question> questions = new ArrayList<>();

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Question>> getQuestions() {
        return ResponseEntity.ok().header("Cache-Control", "max-age" + "=3600").body(questions);
    }


    @GetMapping(params = {"name", "number"})
    public ResponseEntity<Resources<Resource<Question>>> getQuestionsByNameAndNumber(@RequestParam String name,
                                                                      @RequestParam Long number) {
        Resources<Resource<Question>> resources = new Resources<>
                (questions.stream()
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
        return questions.stream()
                .map(q -> q.getName())
                .reduce((s, next) -> String.join(",", s, next))
                .orElse("");
    }

    @GetMapping("/{number}")
    public ResponseEntity<Resource<Question>> getQuestion(@PathVariable Long number) {
        return new ResponseEntity<>(mapToResource(findQuestionByNumber(number).get()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Resources<Resource<Question>>> getAllQuestions() {
        Resources<Resource<Question>> resources = new Resources<>(
                questions.stream().map(q -> mapToResource(q)).collect(Collectors.toList())
        );
        addQuestionsLink(resources, "self");
        addFindQuestionsLink(resources, "questionsByNameAndNumber", null, null);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @GetMapping("/{number}/name")
    public Optional<String> getNameOfQuestion(@PathVariable Long number) {
        return findQuestionByNumber(number)
                .map(Question::getName);
    }

    @GetMapping(params = "name")
    public Stream<Question> findQuestionByName(@RequestParam String name) {
        return questions.stream().filter(q -> name.equals(q.getName()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@RequestBody Question question) {
        questions.add(question);
    }

    @PostMapping(value = "/{number}/answers")
    public void addAnswer(@PathVariable Long number,
                          @RequestBody Answer answer) {
        questions.stream().filter(q -> q.getNumber() == number)
                .findAny()
                .ifPresent(q -> q.getAnswers().add(answer));
    }


    @PutMapping(value = "/{number}")
    private void replaceQuestion(@PathVariable Long number,
                                 @RequestBody Question question) {
        findQuestionByNumber(number)
                .ifPresent(q -> {
                    q.setName(question.getName());
                    q.setAnswers(question.getAnswers());
                });
    }

    @PatchMapping(value = "/{number}")
    private void updateQuestion(@PathVariable Long number,
                                @RequestBody Question question) {
        findQuestionByNumber(number)
                .ifPresent(q -> {
                    if (question.getName() != null)
                        q.setName(question.getName());
                    if (question.getAnswers() != null)
                        q.setAnswers(question.getAnswers());
                });
    }

    @DeleteMapping(value = "/{number}")
    private ResponseEntity<Object> removeQuestion(@PathVariable Long number) {
        boolean removed = questions.removeIf(q -> q.getNumber() == number);
        if (removed) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    private Optional<Question> findQuestionByNumber(Long number) {
        return questions.stream()
                .filter(q -> q.getNumber() == number)
                .findAny();
    }

    private Resource<Question> mapToResource(Question question) {
        Link link = ControllerLinkBuilder.linkTo(QuestionsController.class).slash(question.getNumber()).withSelfRel();
        Resource<Question> questionResource = new Resource<>(findQuestionByNumber(question.getNumber()).get(), link);
        return questionResource;
    }

    private void addFindQuestionsLink(Resources<Resource<Question>> resources, String rel, String name, Long number) {
        resources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(QuestionsController.class)
                .getQuestionsByNameAndNumber(name, number))
                .withRel(rel));
    }

    private void addQuestionsLink(Resources<Resource<Question>> resources, String rel) {
        resources.add(ControllerLinkBuilder.linkTo(QuestionsController.class).withRel(rel));
    }
}
