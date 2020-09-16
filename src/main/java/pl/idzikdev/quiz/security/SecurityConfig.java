package pl.idzikdev.quiz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.idzikdev.quiz.provider.CustomDAOAuthenticationProvider;
import pl.idzikdev.quiz.service.impl.JpaUserDetailsService;


@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JpaUserDetailsService jpaUserDetailsService;


    private CustomDAOAuthenticationProvider customDAOAuthenticationProvider;

    @Autowired
    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, CustomDAOAuthenticationProvider customDAOAuthenticationProvider) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.customDAOAuthenticationProvider = customDAOAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaUserDetailsService);
        auth.authenticationProvider(customDAOAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/swagger_ui.html").permitAll()
                .antMatchers("/admin_panel").hasAuthority("ADMIN")
                .antMatchers("/api/**").permitAll()
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

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
