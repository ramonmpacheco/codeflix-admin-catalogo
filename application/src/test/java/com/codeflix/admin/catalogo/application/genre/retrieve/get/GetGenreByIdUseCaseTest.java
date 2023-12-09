package com.codeflix.admin.catalogo.application.genre.retrieve.get;

import com.codeflix.admin.catalogo.application.UseCaseTest;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.NotFoundException;
import com.codeflix.admin.catalogo.domain.genre.Genre;
import com.codeflix.admin.catalogo.domain.genre.GenreGateway;
import com.codeflix.admin.catalogo.domain.genre.GenreId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;

public class GetGenreByIdUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultGetGenreByIdUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetGenre_shouldReturnGenre() {
        // given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryId.from("123"),
                CategoryId.from("456")
        );

        final var aGenre = Genre.newGenre(expectedName, expectedIsActive)
                .addCategories(expectedCategories);

        final var expectedId = aGenre.getId();

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(aGenre));
        // when
        final var actualGenre = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertEquals(expectedId.getValue(), actualGenre.id());
        Assertions.assertEquals(expectedName, actualGenre.name());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(asString(expectedCategories), actualGenre.categories());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.createdAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.updatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.deletedAt());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenCallsGetGenreAndDoesNotExists_shouldReturnNotFound() {
        // given
        final var expectedErrorMessage = "Genre with ID 123 was not found";

        final var expectedId = GenreId.from("123");

        when(genreGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        // when
        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> {
            useCase.execute(expectedId.getValue());
        });

        // then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
