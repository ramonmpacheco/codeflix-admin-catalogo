package com.codeflix.admin.catalogo.application.category.retrieve.list;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryId;

import java.time.Instant;

public record CategoryLIstOutput(
        CategoryId id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static CategoryLIstOutput from(final Category category) {
        return new CategoryLIstOutput(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }
}
