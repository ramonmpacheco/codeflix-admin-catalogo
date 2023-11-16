package com.codeflix.admin.catalogo.infrastructure.category;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySqlGateway implements CategoryGateway {
    private final CategoryRepository repository;

    public CategoryMySqlGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category c) {
        return save(c);
    }

    @Override
    public void deleteById(final CategoryId id) {
        final var idValue = id.getValue();
       if( this.repository.existsById(idValue)) {
           this.repository.deleteById(idValue);
       }
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return this.repository.findById(id.getValue())
                .map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Category update(final Category c) {
        return save(c);
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery csq) {
        return null;
    }

    private Category save(final Category c) {
        return this.repository.save(CategoryJpaEntity.from(c))
                .toAggregate();
    }
}
