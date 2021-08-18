package br.com.moraesit.storeapitdd.domain.services;

import br.com.moraesit.storeapitdd.domain.entities.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryServiceTest {

    CategoryService categoryService;

    @Test
    @DisplayName("must create a category")
    public void createCategoryTest() {
        Category category = Category.builder()
                .name("T-shirt")
                .description("T-shirt Description")
                .build();

        Category savedCategory = categoryService.create(category);

        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("T-shirt");
        assertThat(savedCategory.getDescription()).isEqualTo("T-shirt Description");
    }
}
