package com.codeflix.admin.catalogo.domain.castmember;

import com.codeflix.admin.catalogo.domain.Identifier;
import com.codeflix.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class CastMemberId extends Identifier {

    private final String value;

    private CastMemberId(final String anId) {
        Objects.requireNonNull(anId);
        this.value = anId;
    }

    public static CastMemberId unique() {
        return CastMemberId.from(IdUtils.uuid());
    }

    public static CastMemberId from(final String anId) {
        return new CastMemberId(anId);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CastMemberId that = (CastMemberId) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}