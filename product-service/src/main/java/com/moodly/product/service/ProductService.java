package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.Category;
import com.moodly.product.domain.Product;
import com.moodly.product.domain.SubCategory;
import com.moodly.product.dto.ProductDto;
import com.moodly.product.repository.CategoryRepository;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> all = productRepository.findAll();
        Map<Long, String> categoryMap = getCategoryMap();
        Map<Long, String> subCategoryMap = getSubCategoryMap();

        return all.stream()
                .map(product -> ProductDto.fromEntity(
                        product,
                        product.getCategoryId() != null ? categoryMap.get(product.getCategoryId()) : null,
                        product.getSubCategoryId() != null ? subCategoryMap.get(product.getSubCategoryId()) : null
                ))
                .toList();
    }

    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.PRODUCTID_NOT_FOUND));

        String categoryName = null;
        if (product.getCategoryId() != null) {
            categoryName = categoryRepository.findById(product.getCategoryId())
                    .map(Category::getName)
                    .orElse(null);
        }

        String subCategoryName = null;
        if (product.getSubCategoryId() != null) {
            subCategoryName = subCategoryRepository.findById(product.getSubCategoryId())
                    .map(SubCategory::getName)
                    .orElse(null);
        }

        return ProductDto.fromEntity(product, categoryName, subCategoryName);
    }

    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        Map<Long, String> categoryMap = getCategoryMap();
        Map<Long, String> subCategoryMap = getSubCategoryMap();

        return products.stream()
                .map(product -> ProductDto.fromEntity(
                        product,
                        product.getCategoryId() != null ? categoryMap.get(product.getCategoryId()) : null,
                        product.getSubCategoryId() != null ? subCategoryMap.get(product.getSubCategoryId()) : null
                ))
                .toList();
    }

    public List<ProductDto> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        List<Product> products = productRepository.searchByName(keyword.trim());
        Map<Long, String> categoryMap = getCategoryMap();
        Map<Long, String> subCategoryMap = getSubCategoryMap();

        return products.stream()
                .map(product -> ProductDto.fromEntity(
                        product,
                        product.getCategoryId() != null ? categoryMap.get(product.getCategoryId()) : null,
                        product.getSubCategoryId() != null ? subCategoryMap.get(product.getSubCategoryId()) : null
                ))
                .toList();
    }

    public List<ProductDto> getHotDealProducts(int limit) {
        List<Product> products = productRepository.findHotDealProducts(limit);
        Map<Long, String> categoryMap = getCategoryMap();
        Map<Long, String> subCategoryMap = getSubCategoryMap();

        return products.stream()
                .map(product -> ProductDto.fromEntity(
                        product,
                        product.getCategoryId() != null ? categoryMap.get(product.getCategoryId()) : null,
                        product.getSubCategoryId() != null ? subCategoryMap.get(product.getSubCategoryId()) : null
                ))
                .toList();
    }

    public List<ProductDto> getTodaySpecialProducts(int limit) {
        List<Product> products = productRepository.findTodaySpecialProducts(limit);
        Map<Long, String> categoryMap = getCategoryMap();
        Map<Long, String> subCategoryMap = getSubCategoryMap();

        return products.stream()
                .map(product -> ProductDto.fromEntity(
                        product,
                        product.getCategoryId() != null ? categoryMap.get(product.getCategoryId()) : null,
                        product.getSubCategoryId() != null ? subCategoryMap.get(product.getSubCategoryId()) : null
                ))
                .toList();
    }

    private Map<Long, String> getCategoryMap() {
        Map<Long, String> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));
        return categoryMap;
    }

    private Map<Long, String> getSubCategoryMap() {
        Map<Long, String> subCategoryMap = subCategoryRepository.findAll().stream()
                .collect(Collectors.toMap(SubCategory::getId, SubCategory::getName));
        return subCategoryMap;
    }

}
