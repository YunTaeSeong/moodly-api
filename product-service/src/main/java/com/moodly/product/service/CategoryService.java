package com.moodly.product.service;

import com.moodly.common.cache.constant.CacheNames;
import com.moodly.product.domain.Category;
import com.moodly.product.domain.SubCategory;
import com.moodly.product.dto.CategoryDto;
import com.moodly.product.dto.SubCategoryDto;
import com.moodly.product.repository.CategoryRepository;
import com.moodly.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Cacheable(
            value = CacheNames.CATEGORY,
            key = "'all'"
    )
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAllCategories();
        return categories.stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Cacheable(
            value = CacheNames.SUB_CATEGORY,
            key = "#categoryId"
    )
    public List<SubCategoryDto> getSubCategoriesByCategoryId(Long categoryId) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId);
        return subCategories.stream()
                .map(SubCategoryDto::fromEntity)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

