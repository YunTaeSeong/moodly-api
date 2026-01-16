package com.moodly.product.service;

import com.moodly.product.domain.Category;
import com.moodly.product.domain.SubCategory;
import com.moodly.product.dto.CategoryDto;
import com.moodly.product.dto.SubCategoryDto;
import com.moodly.product.repository.CategoryRepository;
import com.moodly.product.repository.SubCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @DisplayName("모든 카테고리")
    @Test
    void 모든_카테고리() {
        // given
        Category c1 = mock(Category.class);
        when(c1.getId()).thenReturn(1L);
        when(c1.getName()).thenReturn("의류");

        when(categoryRepository.findAllCategories()).thenReturn(List.of(c1));

        // when
        List<CategoryDto> result = categoryService.getAllCategories();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(categoryRepository).findAllCategories();
    }

    @DisplayName("카테고리 : 서브 카테고리")
    @Test
    void 카테고리_서브_카테고리() {
        // given
        Long categoryId = 1L;

        SubCategory s1 = mock(SubCategory.class);
        when(s1.getId()).thenReturn(10L);
        when(s1.getName()).thenReturn("의류 서브카테고리");

        when(subCategoryRepository.findByCategoryId(categoryId)).thenReturn(List.of(s1));

        // when
        List<SubCategoryDto> result = categoryService.getSubCategoriesByCategoryId(categoryId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(subCategoryRepository).findByCategoryId(categoryId);
    }



}