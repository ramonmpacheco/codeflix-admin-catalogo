package com.codeflix.admin.catalogo.application.genre.retrieve.get;

import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreOutput(
        String id,
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static GenreOutput from(final Genre aGenre) {
        return new GenreOutput(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream()
                        .map(CategoryId::getValue)
                        .toList(),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt()
        );
    }
}
