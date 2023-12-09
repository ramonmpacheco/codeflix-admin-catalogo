package com.codeflix.admin.catalogo.application.genre.retrieve.list;

import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreListOutput(
        String id,
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant deletedAt
) {
    public static GenreListOutput from(final Genre aGenre) {
        return new GenreListOutput(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream()
                        .map(CategoryId::getValue)
                        .toList(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }
}
