package com.codeflix.admin.catalogo.application.castmember.create;

import com.codeflix.admin.catalogo.domain.castmember.CastMember;
import com.codeflix.admin.catalogo.domain.castmember.CastMemberId;

public record CreateCastMemberOutput(
        String id
) {
    public static CreateCastMemberOutput from(final CastMemberId anId) {
        return new CreateCastMemberOutput(anId.getValue());
    }

    public static CreateCastMemberOutput from(final CastMember aMember) {
        return from(aMember.getId());
    }
}
