package br.com.moraesit.storeapitdd.api.v1.controllers;

import br.com.moraesit.storeapitdd.api.v1.dto.CategoryDTO;
import br.com.moraesit.storeapitdd.api.v1.mapper.CategoryMapper;
import br.com.moraesit.storeapitdd.domain.entities.Category;
import br.com.moraesit.storeapitdd.domain.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categories")
public class CategoryControllerV1 {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public CategoryControllerV1(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.toDTO(category));
    }
}
