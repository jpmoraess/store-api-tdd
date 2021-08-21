package br.com.moraesit.storeapitdd.domain.services.impl;

import br.com.moraesit.storeapitdd.domain.entities.Category;
import br.com.moraesit.storeapitdd.domain.exceptions.DomainException;
import br.com.moraesit.storeapitdd.domain.repositories.CategoryRepository;
import br.com.moraesit.storeapitdd.domain.services.CategoryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName()))
            throw new DomainException("Name already exists");
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found."));
    }
}
