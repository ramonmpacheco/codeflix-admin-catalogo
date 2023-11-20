package com.codeflix.admin.catalogo.infrastructure.persistence;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.MySqlGatewayTest;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySqlGatewayTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidName_whenCallSave_thenReturnError() {
        final var expectedPropertyName = "name";
        final var expectedPropertyMessage = "not-null property references a null or transient value : com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity.name";
        final var category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var entity = CategoryJpaEntity.from(category);
        entity.setName(null);

        final var ex =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, ex.getCause());
        Assertions.assertEquals(expectedPropertyName, cause.getPropertyName());
        Assertions.assertEquals(expectedPropertyMessage, cause.getMessage());
    }

    @Test
    public void givenAnInvalidCreatedAt_whenCallSave_thenReturnError() {
        final var expectedPropertyName = "createdAt";
        final var expectedPropertyMessage = "not-null property references a null or transient value : com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity.createdAt";
        final var category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var entity = CategoryJpaEntity.from(category);
        entity.setCreatedAt(null);

        final var ex =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, ex.getCause());
        Assertions.assertEquals(expectedPropertyName, cause.getPropertyName());
        Assertions.assertEquals(expectedPropertyMessage, cause.getMessage());
    }

    @Test
    public void givenAnInvalidUpdatedAt_whenCallSave_thenReturnError() {
        final var expectedPropertyName = "updatedAt";
        final var expectedPropertyMessage = "not-null property references a null or transient value : com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity.updatedAt";
        final var category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var entity = CategoryJpaEntity.from(category);
        entity.setUpdatedAt(null);

        final var ex =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, ex.getCause());
        Assertions.assertEquals(expectedPropertyName, cause.getPropertyName());
        Assertions.assertEquals(expectedPropertyMessage, cause.getMessage());
    }
}
