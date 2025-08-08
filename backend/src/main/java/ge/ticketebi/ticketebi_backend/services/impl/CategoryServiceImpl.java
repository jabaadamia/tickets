package ge.ticketebi.ticketebi_backend.services.impl;

import ge.ticketebi.ticketebi_backend.domain.dto.CategoryDto;
import ge.ticketebi.ticketebi_backend.domain.entities.CategoryEntity;
import ge.ticketebi.ticketebi_backend.exceptions.CategoryNotFoundException;
import ge.ticketebi.ticketebi_backend.mappers.Mapper;
import ge.ticketebi.ticketebi_backend.repositories.CategoryRepository;
import ge.ticketebi.ticketebi_backend.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper<CategoryEntity, CategoryDto> categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            Mapper<CategoryEntity, CategoryDto> categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        CategoryEntity entity = categoryRepository.findById(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category with name " + name + " not found"));
        return categoryMapper.mapTo(entity);

    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity entity = categoryMapper.mapFrom(categoryDto);
        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.mapTo(saved);
    }

    @Override
    public void deleteCategory(String name) {
        CategoryEntity category = categoryRepository.findById(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category with name " + name + " not found"));
        categoryRepository.delete(category);
    }
}
