package com.example.testbook.Service.CategoryService;
import com.example.testbook.Repository.CategoryRepository;
import com.example.testbook.Service.Response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    public List<SelectOptionResponse> findAll(){
        return categoryRepository.findAll().stream()
                .map(category -> new SelectOptionResponse(category.getId().toString(), category.getName()))
                .collect(Collectors.toList());
    }
}
