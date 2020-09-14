package pl.idzikdev.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.idzikdev.quiz.domain.User;
import pl.idzikdev.quiz.repository.UserRepository;
import pl.idzikdev.quiz.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    UserRepository userRepository;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUpUser(User user) {
        Assert.isNull(user.getIdUser(),"User is already signed");
        return userRepository.save(user);
    }
}
