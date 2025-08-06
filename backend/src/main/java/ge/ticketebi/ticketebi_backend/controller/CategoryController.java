package ge.ticketebi.ticketebi_backend.controller;

import ge.ticketebi.ticketebi_backend.entities.Category;
import ge.ticketebi.ticketebi_backend.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return repository.findAll();
    }

    @GetMapping("/categories/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Optional<Category> category = repository.findById(name);
        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
