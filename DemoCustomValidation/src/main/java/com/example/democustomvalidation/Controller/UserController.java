package com.example.democustomvalidation.Controller;
import com.example.democustomvalidation.Domain.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class UserController {

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();


        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            // Xử lý đăng ký người dùng
            modelAndView.setViewName("registration-success");
        }

        return modelAndView;
    }
}
