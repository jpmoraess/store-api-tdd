package br.com.moraesit.storeapitdd.api.v1.mapper;

import br.com.moraesit.storeapitdd.api.v1.dto.CategoryDTO;
import br.com.moraesit.storeapitdd.domain.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
