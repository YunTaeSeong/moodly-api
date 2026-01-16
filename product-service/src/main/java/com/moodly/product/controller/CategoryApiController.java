package com.moodly.product.controller;

import com.moodly.product.dto.CategoryDto;
import com.moodly.product.dto.SubCategoryDto;
import com.moodly.product.response.CategoryResponse;
import com.moodly.product.response.SubCategoryResponse;
import com.moodly.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * 전체 카테고리 목록
     */
    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        return categoryDtos.stream()
                .map(CategoryResponse::response)
                .toList();
    }

    /**
     * 카테고리의 서브카테고리 목록
     */
    @GetMapping("/{categoryId}/subcategories")
    public List<SubCategoryResponse> getSubCategoriesByCategoryId(
            @PathVariable Long categoryId
    ) {
        List<SubCategoryDto> subCategoryDtos = categoryService.getSubCategoriesByCategoryId(categoryId);
        return subCategoryDtos.stream()
                .map(SubCategoryResponse::response)
                .toList();
    }
}

