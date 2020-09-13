package pl.idzikdev.quiz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/webapp")
public class WebController {

    @GetMapping
    public ModelAndView userPanel(ModelAndView mav) {
        mav.setViewName("user_panel");
        return mav;
    }
}
