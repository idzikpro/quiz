package pl.idzikdev.quiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.idzikdev.quiz.domain.User;
import pl.idzikdev.quiz.service.SignUpService;

@RestController
public class SignUpController {

    private SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }


    @PostMapping("/api/signUser")
    public String signUp(String username,String password){
        User user=new User(username,password);
        signUpService.signUpUser(user);
        return "User signed up";
    }
}
