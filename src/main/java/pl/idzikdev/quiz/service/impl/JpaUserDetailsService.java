package pl.idzikdev.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.idzikdev.quiz.domain.User;
import pl.idzikdev.quiz.repository.UserRepository;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUserName = userRepository.findByusername(userName);
        if (!optionalUserName.isPresent()) {
            throw new UsernameNotFoundException("No user found exception" + userName);
        }
        return optionalUserName.get();
    }
}
