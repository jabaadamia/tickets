package ge.ticketebi.ticketebi_backend.controllers;

import ge.ticketebi.ticketebi_backend.domain.dto.CategoryDto;
import ge.ticketebi.ticketebi_backend.domain.entities.CategoryEntity;
import ge.ticketebi.ticketebi_backend.mappers.Mapper;
import ge.ticketebi.ticketebi_backend.services.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    private Mapper<CategoryEntity, CategoryDto> categoryMapper;

    public CategoryController(CategoryService categoryService, Mapper<CategoryEntity, CategoryDto> categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping(path = "/categories")
    public CategoryDto createCategory(@RequestBody CategoryDto category) {
        CategoryEntity categoryEntity = categoryMapper.mapFrom(category);
        CategoryEntity savedCategoryEntity = categoryService.createCategory(categoryEntity);
        return categoryMapper.mapTo(savedCategoryEntity);
    }

}
