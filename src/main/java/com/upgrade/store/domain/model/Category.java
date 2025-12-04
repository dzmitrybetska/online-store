package com.upgrade.store.domain.model;

import com.upgrade.store.domain.model.common.DataEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"parentCategory", "subCategories", "createdByUser"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends DataEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Category> subCategories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User createdByUser;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
