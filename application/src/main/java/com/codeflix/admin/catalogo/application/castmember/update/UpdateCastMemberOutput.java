package com.codeflix.admin.catalogo.application.castmember.update;

import com.codeflix.admin.catalogo.domain.castmember.CastMember;
import com.codeflix.admin.catalogo.domain.castmember.CastMemberId;

public record UpdateCastMemberOutput(String id) {
    public static UpdateCastMemberOutput from(final CastMemberId anId) {
        return new UpdateCastMemberOutput(anId.getValue());
    }
    public static UpdateCastMemberOutput from(final CastMember aMember) {
        return from(aMember.getId());
    }
}
