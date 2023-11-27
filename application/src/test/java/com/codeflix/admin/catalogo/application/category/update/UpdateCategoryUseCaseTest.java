package com.codeflix.admin.catalogo.application.category.update;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_thenShouldReturnCategoryId() {
        final var category = Category.newCategory("Film", null, true);

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

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var out = useCase.execute(command).get();

        assertNotNull(out);
        assertNotNull(out.id());

        Mockito.verify(gateway, times(1)).findById(eq(expectedId));
        Mockito.verify(gateway, times(1)).update(argThat(
                update ->
                        Objects.equals(expectedName, update.getName()) &&
                                Objects.equals(expectedDesc, update.getDescription()) &&
                                Objects.equals(expectedIsActive, update.isActive()) &&
                                Objects.equals(expectedId, update.getId()) &&
                                Objects.equals(update.getCreatedAt(), category.getCreatedAt()) &&
                                category.getUpdatedAt().isBefore(update.getUpdatedAt()) &&
                                Objects.isNull(update.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var category = Category.newCategory("Film", null, true);
        final String expectedName = null;
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDesc, expectedIsActive);
        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(category.clone()));
        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_thenShouldReturnInactiveCategoryId() {
        final var category = Category.newCategory("Film", null, true);

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

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());


        final var out = useCase.execute(command).get();

        assertNotNull(out);
        assertNotNull(out.id());

        Mockito.verify(gateway, times(1)).findById(eq(expectedId));
        Mockito.verify(gateway, times(1)).update(argThat(
                update ->
                        Objects.equals(expectedName, update.getName()) &&
                                Objects.equals(expectedDesc, update.getDescription()) &&
                                Objects.equals(expectedIsActive, update.isActive()) &&
                                Objects.equals(expectedId, update.getId()) &&
                                Objects.equals(update.getCreatedAt(), category.getCreatedAt()) &&
                                category.getUpdatedAt().isBefore(update.getUpdatedAt()) &&
                                Objects.nonNull(update.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnException() {
        final var category = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;
        final var expectedId = category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDesc, expectedIsActive);

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(gateway, times(1)).update(argThat(
                update ->
                        Objects.equals(expectedName, update.getName()) &&
                                Objects.equals(expectedDesc, update.getDescription()) &&
                                Objects.equals(expectedIsActive, update.isActive()) &&
                                Objects.equals(expectedId, update.getId()) &&
                                Objects.equals(update.getCreatedAt(), category.getCreatedAt()) &&
                                category.getUpdatedAt().isBefore(update.getUpdatedAt()) &&
                                Objects.isNull(update.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {

        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedErrMessage = "Category with ID 123 was not found";

        final var expectedId = "123";
        final var command = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDesc,
                expectedIsActive
        );

        when(gateway.findById(eq(CategoryId.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, ()-> useCase.execute(command));

        assertEquals(expectedErrMessage, actualException.getMessage());

        Mockito.verify(gateway, times(1)).findById(eq(CategoryId.from(expectedId)));
        Mockito.verify(gateway, times(0)).update(any());
    }
}
