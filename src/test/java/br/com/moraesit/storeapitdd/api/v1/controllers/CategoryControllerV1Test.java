package br.com.moraesit.storeapitdd.api.v1.controllers;

import br.com.moraesit.storeapitdd.api.v1.dto.CategoryDTO;
import br.com.moraesit.storeapitdd.api.v1.mapper.CategoryMapper;
import br.com.moraesit.storeapitdd.domain.entities.Category;
import br.com.moraesit.storeapitdd.domain.exceptions.DomainException;
import br.com.moraesit.storeapitdd.domain.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CategoryControllerV1Test {

    static String CATEGORY_V1 = "/v1/categories";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryMapper categoryMapper;

    @Test
    @DisplayName("must create a create category successfully")
    public void createCategoryTest() throws Exception {
        var categoryDTO = categoryDTO(null, "T-shirt", "Description");

        var savedCategory = category(1L, "T-shirt", "Description");

        BDDMockito.when(categoryMapper.toEntity(Mockito.any(CategoryDTO.class)))
                .thenReturn(category(null, categoryDTO.getName(), categoryDTO.getDescription()));

        BDDMockito.given(categoryService.create(Mockito.any(Category.class))).willReturn(savedCategory);

        BDDMockito.when(categoryMapper.toDTO(Mockito.any(Category.class)))
                .thenReturn(categoryDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription()));

        var json = new ObjectMapper().writeValueAsString(categoryDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CATEGORY_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("T-shirt"));
    }

    @Test
    @DisplayName("must validate the creation of a category with invalid data")
    public void createInvalidCategoryTest() throws Exception {
        var json = new ObjectMapper().writeValueAsString(new CategoryDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CATEGORY_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").isNotEmpty())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("status").value(400));
    }

    @Test
    @DisplayName("must validate the creation of a category with duplicated name")
    public void createCategoryWithDuplicatedName() throws Exception {
        var categoryDTO = categoryDTO(1L, "T-shirt", "Description");

        var json = new ObjectMapper().writeValueAsString(categoryDTO);

        BDDMockito.when(categoryMapper.toEntity(Mockito.any(CategoryDTO.class)))
                .thenReturn(category(null, categoryDTO.getName(), categoryDTO.getDescription()));

        BDDMockito.given(categoryService.create(Mockito.any(Category.class)))
                .willThrow(new DomainException("Name already exists"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CATEGORY_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Name already exists"))
                .andExpect(jsonPath("status").value(400));
    }

    @Test
    @DisplayName("get category details test")
    public void getCategoryDetailsTest() throws Exception {
        Long id = 1L;

        Category category = category(id, "T-shirt", "T-shirt description");

        BDDMockito.given(categoryService.getById(id)).willReturn(category);

        CategoryDTO dto = categoryDTO(id, category.getName(), category.getDescription());

        BDDMockito.when(categoryMapper.toDTO(Mockito.any(Category.class)))
                .thenReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CATEGORY_V1.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value("T-shirt"))
                .andExpect(jsonPath("description").value("T-shirt description"));
    }

    @Test
    @DisplayName("category not found test")
    public void categoryNotFoundTest() throws Exception {
        Long id = 1L;

        BDDMockito.given(categoryService.getById(Mockito.anyLong()))
                .willThrow(new EntityNotFoundException("Category not found."));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CATEGORY_V1.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value(404))
                .andExpect(jsonPath("message").value("Category not found."));
    }

    private CategoryDTO categoryDTO(Long id, String name, String description) {
        return CategoryDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    private Category category(Long id, String name, String description) {
        return Category.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}

