package com.codeflix.admin.catalogo.application.category.retrieve.list;

import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.pagination.SearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoryUseCase extends ListCategoryUseCase{
    private final CategoryGateway gateway;

    public DefaultListCategoryUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull( gateway);
    }

    @Override
    public Pagination<CategoryLIstOutput> execute(final SearchQuery query) {
        return this.gateway.findAll(query)
                .map(CategoryLIstOutput::from);
    }
}
