package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryId;

public record CreateCategoryOutput(
        String id
) {
    public static CreateCategoryOutput from(final String id) {
        return new CreateCategoryOutput(id);
    }
    public static CreateCategoryOutput from(final Category c) {
        return new CreateCategoryOutput(c.getId().getValue());
    }
}
