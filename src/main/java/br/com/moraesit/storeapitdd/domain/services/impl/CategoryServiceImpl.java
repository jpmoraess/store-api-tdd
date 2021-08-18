package br.com.moraesit.storeapitdd.domain.services.impl;

import br.com.moraesit.storeapitdd.domain.entities.Category;
import br.com.moraesit.storeapitdd.domain.repositories.CategoryRepository;
import br.com.moraesit.storeapitdd.domain.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }
}
