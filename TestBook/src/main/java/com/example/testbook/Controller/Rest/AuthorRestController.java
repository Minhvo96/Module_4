package com.example.testbook.Controller.Rest;
import com.example.testbook.Repository.AuthorRepository;
import com.example.testbook.Service.Response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("api/authors")
@AllArgsConstructor
public class AuthorRestController {
    private final AuthorRepository authorRepository;

    @GetMapping
    public List<SelectOptionResponse> getSelectOption() {
        return authorRepository.findAll().stream().map(author -> new SelectOptionResponse(author.getId().toString(), author.getName())).collect(Collectors.toList());
    }
}
