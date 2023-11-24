package com.codeflix.admin.catalogo.infrastructure.category.presenters;

import com.codeflix.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import com.codeflix.admin.catalogo.infrastructure.category.models.CategoryApiOutput;

public interface CategoryApiPresenter {
    static CategoryApiOutput present(final CategoryOutput out) {
        return new CategoryApiOutput(
                out.id().getValue(),
                out.name(),
                out.description(),
                out.isActive(),
                out.createdAt(),
                out.updatedAt(),
                out.deletedAt()
        );
    }
}
