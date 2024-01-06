package com.codeflix.admin.catalogo.domain.video;

import com.codeflix.admin.catalogo.domain.Identifier;
import com.codeflix.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class VideoId extends Identifier {
    private final String value;

    private VideoId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static VideoId from(final String anId) {
        return new VideoId(anId.toLowerCase());
    }

    public static VideoId unique() {
        return VideoId.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoId that = (VideoId) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}