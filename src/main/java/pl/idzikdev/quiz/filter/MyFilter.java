package pl.idzikdev.quiz.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("Hello. Filter is watching!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
