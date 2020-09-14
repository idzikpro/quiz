package pl.idzikdev.quiz.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.idzikdev.quiz.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InMemoryUserDetailsServiceImpl implements UserDetailsService {

    private static final String USER_WITH_USERNAME_S_DOES_NOT_EXIST = "User with username %s does not exist";

    private static final String USERNAME_CAN_NOT_BE_NULL = "Username can not be null";

    private List<UserDetails> users;


    public InMemoryUserDetailsServiceImpl() {
        users = new ArrayList<UserDetails>();
        users.add(new User("user","user",Arrays.asList("USER")));
        users.add(new User("foo","bar",Arrays.asList("USER")));
        users.add(new User("admin","admin",Arrays.asList("ADMIN")));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, USERNAME_CAN_NOT_BE_NULL);
        Optional<UserDetails> optionalUser = users.stream().filter(u -> username.equals(u.getUsername())).findFirst();
        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(String.format(USER_WITH_USERNAME_S_DOES_NOT_EXIST, username));
        }
        return optionalUser.get();
    }

}
