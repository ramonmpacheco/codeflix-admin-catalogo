package com.codeflix.admin.catalogo.infrastructure.category.presenters;

import com.codeflix.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import com.codeflix.admin.catalogo.application.category.retrieve.list.CategoryLIstOutput;
import com.codeflix.admin.catalogo.infrastructure.category.models.CategoryResponse;
import com.codeflix.admin.catalogo.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {
    static CategoryResponse present(final CategoryOutput out) {
        return new CategoryResponse(
                out.id().getValue(),
                out.name(),
                out.description(),
                out.isActive(),
                out.createdAt(),
                out.updatedAt(),
                out.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryLIstOutput out) {
        return new CategoryListResponse(
                out.id().getValue(),
                out.name(),
                out.description(),
                out.isActive(),
                out.createdAt(),
                out.deletedAt()
        );
    }
}
