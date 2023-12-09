package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.application.UseCaseTest;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


public class CreateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenShouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var command = CreateCategoryCommand.with(expectedName, expectedDesc, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var out = useCase.execute(command).get();

        assertNotNull(out);
        assertNotNull(out.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(c ->
                Objects.equals(expectedName, c.getName()) &&
                        Objects.equals(expectedDesc, c.getDescription()) &&
                        Objects.equals(expectedIsActive, c.isActive()) &&
                        Objects.nonNull(c.getId()) &&
                        Objects.nonNull(c.getCreatedAt()) &&
                        Objects.nonNull(c.getUpdatedAt()) &&
                        Objects.isNull(c.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var command = CreateCategoryCommand.with(expectedName, expectedDesc, expectedIsActive);
        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_thenShouldReturnInactiveCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var command = CreateCategoryCommand.with(expectedName, expectedDesc, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var out = useCase.execute(command).get();

        assertNotNull(out);
        assertNotNull(out.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(c ->
                Objects.equals(expectedName, c.getName()) &&
                        Objects.equals(expectedDesc, c.getDescription()) &&
                        Objects.equals(expectedIsActive, c.isActive()) &&
                        Objects.nonNull(c.getId()) &&
                        Objects.nonNull(c.getCreatedAt()) &&
                        Objects.nonNull(c.getUpdatedAt()) &&
                        Objects.nonNull(c.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnException() {
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var command = CreateCategoryCommand.with(expectedName, expectedDesc, expectedIsActive);

        when(categoryGateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).create(argThat(c ->
                Objects.equals(expectedName, c.getName()) &&
                        Objects.equals(expectedDesc, c.getDescription()) &&
                        Objects.equals(expectedIsActive, c.isActive()) &&
                        Objects.nonNull(c.getId()) &&
                        Objects.nonNull(c.getCreatedAt()) &&
                        Objects.nonNull(c.getUpdatedAt()) &&
                        Objects.isNull(c.getDeletedAt())
        ));
    }
}
