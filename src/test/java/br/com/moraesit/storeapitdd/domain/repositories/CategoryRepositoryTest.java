package br.com.moraesit.storeapitdd.domain.repositories;

import br.com.moraesit.storeapitdd.domain.entities.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("must return true when a category with the same name entered exists in the database")
    public void returnTrueWhenNameExists() {
        String name = "T-shirt";

        Category category = Category.builder()
                .name("T-shirt")
                .description("T-shirt description")
                .build();

        entityManager.persist(category);

        Boolean exists = categoryRepository.existsByName(name);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("must return false when a category with the same name doesnt exists in the database")
    public void returnfalseWhenNameDoesntExists() {
        String name = "T-shirt";

        Boolean exists = categoryRepository.existsByName(name);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("must return category by id")
    public void returnCategoryByIdTest() {
        Category category = Category.builder()
                .name("T-shirt")
                .description("T-shirt")
                .build();

        entityManager.persist(category);

        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());

        assertThat(categoryOptional.isPresent()).isTrue();
    }
}
