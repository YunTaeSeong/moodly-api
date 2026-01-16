package com.moodly.product.repository;

import com.moodly.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c order by c.displayOrder asc")
    List<Category> findAllCategories();
}

