package com.codeflix.admin.catalogo.infrastructure.category;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.pagination.SearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.codeflix.admin.catalogo.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.codeflix.admin.catalogo.infrastructure.utils.SpecificationUtils.like;

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
        if (this.repository.existsById(idValue)) {
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
    public Pagination<Category> findAll(final SearchQuery csq) {
        // Paginação
        final var page = PageRequest.of(
                csq.page(),
                csq.perPage(),
                Sort.by(Sort.Direction.fromString(csq.direction()), csq.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(csq.terms())
                .filter(str -> !str.isBlank())
                .map(str -> SpecificationUtils
                        .<CategoryJpaEntity>like("name", str)
                        .or(like("description", str))
                )
                .orElse(null);

        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }

    private Category save(final Category c) {
        return this.repository.save(CategoryJpaEntity.from(c))
                .toAggregate();
    }
}
