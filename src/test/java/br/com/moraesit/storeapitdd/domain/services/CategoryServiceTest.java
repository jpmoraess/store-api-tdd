package br.com.moraesit.storeapitdd.domain.services;

import br.com.moraesit.storeapitdd.domain.entities.Category;
import br.com.moraesit.storeapitdd.domain.repositories.CategoryRepository;
import br.com.moraesit.storeapitdd.domain.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryServiceTest {

    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        this.categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    @DisplayName("must create a category")
    public void createCategoryTest() {
        Category category = Category.builder()
                .name("T-shirt")
                .description("T-shirt Description")
                .build();

        Mockito.when(categoryRepository.save(category)).thenReturn(Category.builder()
                .id(1L)
                .name("T-shirt")
                .description("T-shirt Description")
                .build());

        Category savedCategory = categoryService.create(category);

        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("T-shirt");
        assertThat(savedCategory.getDescription()).isEqualTo("T-shirt Description");
    }
}
