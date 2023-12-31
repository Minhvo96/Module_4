package com.example.filmproject.Controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/films")
@AllArgsConstructor
public class FilmRestController {
    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid FilmSaveRequest request){
        return new ResponseEntity<>(filmService.create(request), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<FilmListResponse>> list(){
        return new ResponseEntity<>(filmService.getAll(), HttpStatus.CREATED);
    }
}
