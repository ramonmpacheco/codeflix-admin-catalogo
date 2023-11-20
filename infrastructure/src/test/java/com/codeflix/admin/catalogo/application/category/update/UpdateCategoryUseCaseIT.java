package com.codeflix.admin.catalogo.application.category.update;

import com.codeflix.admin.catalogo.IntegrationTest;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@IntegrationTest
public class UpdateCategoryUseCaseIT {
    @Autowired
    private UpdateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_thenShouldReturnCategoryId() {
        final var category = Category.newCategory("Film", null, true);

        save(category);

        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedId = category.getId();
        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDesc,
                expectedIsActive
        );

        Assertions.assertEquals(1, categoryRepository.count());

        final var out = useCase.execute(command).get();

        assertNotNull(out);
        assertNotNull(out.id());

        final var actualCategory = categoryRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDesc, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(category.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var category = Category.newCategory("Film", null, true);
        save(category);

        final String expectedName = null;
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDesc, expectedIsActive);

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_thenShouldReturnInactiveCategoryId() {
        final var category = Category.newCategory("Film", null, true);
        save(category);

        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var expectedId = category.getId();
        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDesc,
                expectedIsActive
        );

        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());


        final var out = useCase.execute(command).get();

        assertNotNull(out);
        assertNotNull(out.id());

        final var actualCategory = categoryRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDesc, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(category.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnException() {
        final var category = Category.newCategory("Film", null, true);
        save(category);

        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDesc, expectedIsActive);

        Mockito.doThrow(new IllegalStateException(expectedErrorMessage))
                        .when(categoryGateway).update(any());

        final var notification = useCase.execute(command).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());

        final var actualCategory = categoryRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(category.getName(), actualCategory.getName());
        Assertions.assertEquals(category.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(category.isActive(), actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), actualCategory.getDeletedAt());
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {

        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedErrMessage = "Category with Id 123 was not-found";
        final var expectedErrorCount = 1;

        final var expectedId = "123";
        final var command = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDesc,
                expectedIsActive
        );

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> useCase.execute(command));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrMessage, actualException.getErrors().get(0).message());

    }

    private void save(final Category... categoryList) {
        var categories = Arrays.stream(categoryList).map(CategoryJpaEntity::from).toList();
        categoryRepository.saveAllAndFlush(categories);
    }
}
