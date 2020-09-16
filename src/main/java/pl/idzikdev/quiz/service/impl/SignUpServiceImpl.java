package pl.idzikdev.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.idzikdev.quiz.domain.User;
import pl.idzikdev.quiz.repository.UserRepository;
import pl.idzikdev.quiz.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signUpUser(User user) {
        Assert.isNull(user.getIdUser(), "User is already signed");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
