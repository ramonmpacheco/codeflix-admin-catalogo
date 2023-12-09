package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.pagination.SearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import java.util.List;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category c);
    void deleteById(CategoryId id);
    Optional<Category> findById(CategoryId id);
    Category update(Category c);
    Pagination<Category> findAll(SearchQuery csq);
    List<CategoryId> existsByIds(Iterable<CategoryId> ids);
}
