package com.codeflix.admin.catalogo.domain.genre;

import com.codeflix.admin.catalogo.domain.Identifier;
import com.codeflix.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class GenreId extends Identifier {
    private final String value;

    private GenreId(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreId unique() {
        return GenreId.from(IdUtils.uuid());
    }

    public static GenreId from(final String anId) {
        return new GenreId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreId that = (GenreId) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
