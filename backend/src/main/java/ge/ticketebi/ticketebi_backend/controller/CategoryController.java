package ge.ticketebi.ticketebi_backend.controller;

import ge.ticketebi.ticketebi_backend.entities.Category;
import ge.ticketebi.ticketebi_backend.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
