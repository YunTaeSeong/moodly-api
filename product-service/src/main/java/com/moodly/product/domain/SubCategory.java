package com.moodly.product.domain;

import com.moodly.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "sub_categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SubCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "category_id")
    private Long categoryId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;
}
