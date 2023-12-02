package com.codeflix.admin.catalogo.domain.genre;

import com.codeflix.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class GenreId extends Identifier {
    private final String value;

    private GenreId(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreId unique() {
        return GenreId.from(UUID.randomUUID());
    }

    public static GenreId from(final String anId) {
        return new GenreId(anId);
    }

    public static GenreId from(final UUID anId) {
        return new GenreId(anId.toString().toLowerCase());
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
