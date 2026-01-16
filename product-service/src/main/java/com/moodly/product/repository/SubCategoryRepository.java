package com.moodly.product.repository;

import com.moodly.product.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("select sc from SubCategory sc where sc.categoryId = :categoryId order by sc.displayOrder asc")
    List<SubCategory> findByCategoryId(Long categoryId);
}

