package com.codeflix.admin.catalogo.infrastructure.genre.persistence;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

@Embeddable
public class GenreCategoryId implements Serializable {
    @Column(name = "genre_id", nullable = false)
    private String genreId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    public GenreCategoryId() {}

    private GenreCategoryId(final String aGenreId, final String aCategoryId) {
        this.genreId = aGenreId;
        this.categoryId = aCategoryId;
    }

    public static GenreCategoryId from(final String aGenreId, final String aCategoryId) {
        return new GenreCategoryId(aGenreId, aCategoryId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreCategoryId that = (GenreCategoryId) o;
        return Objects.equals(getGenreId(), that.getGenreId()) && Objects.equals(getCategoryId(), that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenreId(), getCategoryId());
    }

    public String getGenreId() {
        return genreId;
    }

    public GenreCategoryId setGenreId(String genreId) {
        this.genreId = genreId;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public GenreCategoryId setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
