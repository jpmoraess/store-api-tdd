package br.com.moraesit.storeapitdd.domain.repositories;

import br.com.moraesit.storeapitdd.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
