package com.example.testbook.Controller;

import com.example.testbook.Domain.Category;
import com.example.testbook.Service.AuthorService.AuthorService;
import com.example.testbook.Service.CategoryService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class HomeController {

    private final AuthorService authorService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("authors", authorService.findAll());
        view.addObject("categories", categoryService.findAll());
        return view;
    }
}
