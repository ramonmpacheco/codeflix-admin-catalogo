package com.codeflix.admin.catalogo.domain.video;

import com.codeflix.admin.catalogo.domain.castmember.CastMemberId;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.genre.GenreId;

import java.util.Set;

public record VideoSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        Set<CastMemberId> castMembers,
        Set<CategoryId> categories,
        Set<GenreId> genres
) {
}
