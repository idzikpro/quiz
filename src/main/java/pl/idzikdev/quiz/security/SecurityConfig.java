package pl.idzikdev.quiz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        System.out.println("Hello");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/swagger_ui.html").permitAll()
                .antMatchers("/admin_panel").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user_panel", true)
                .and()
                .logout()
                .logoutUrl("/user_logout")
                .logoutSuccessUrl("/login?logut")
                .deleteCookies("cookies");
    }
}
