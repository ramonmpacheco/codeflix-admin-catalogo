package com.codeflix.admin.catalogo.application.category.retrieve.get;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_thenReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);
        final var expectedId = category.getId();

        when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDesc, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.createdAt());
        Assertions.assertEquals(category.getUpdatedAt(), actualCategory.updatedAt());
        Assertions.assertEquals(category.getDeletedAt(), actualCategory.deletedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_thenReturnNotFound() {
        final var expectedErrorMessage = "Category with ID 123 was not found";

        final var expectedId = CategoryId.from("123");

        when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenReturnException() {
        final var expectedErrorMessage = "Gateway error";

        final var expectedId = CategoryId.from("123");

        when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
