package pl.idzikdev.quiz.service;

import org.springframework.stereotype.Service;
import pl.idzikdev.quiz.domain.User;


public interface SignUpService {
    User signUpUser(User user);
}
