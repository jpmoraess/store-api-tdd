package br.com.moraesit.storeapitdd.api.v1.controllers;

import br.com.moraesit.storeapitdd.api.v1.dto.CategoryDTO;
import br.com.moraesit.storeapitdd.api.v1.mapper.CategoryMapper;
import br.com.moraesit.storeapitdd.domain.entities.Category;
import br.com.moraesit.storeapitdd.domain.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
public class CategoryControllerV1 {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public CategoryControllerV1(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable Long id) {
        return categoryMapper.toDTO(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody @Validated CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.toDTO(category));
    }
}
