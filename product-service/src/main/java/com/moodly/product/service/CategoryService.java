package com.moodly.product.service;

import com.moodly.product.domain.Category;
import com.moodly.product.domain.SubCategory;
import com.moodly.product.dto.CategoryDto;
import com.moodly.product.dto.SubCategoryDto;
import com.moodly.product.repository.CategoryRepository;
import com.moodly.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAllCategories();
        return categories.stream()
                .map(CategoryDto::fromEntity)
                .toList();
    }

    public List<SubCategoryDto> getSubCategoriesByCategoryId(Long categoryId) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId);
        return subCategories.stream()
                .map(SubCategoryDto::fromEntity)
                .toList();
    }
}

