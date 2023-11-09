package com.codeflix.admin.catalogo.application.category.retrieve.list;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListCategoryUseCaseTest {
    @InjectMocks
    private DefaultListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCategories_thenReturnCategories() {
        final var categories = List.of(
                Category.newCategory("Filmes", null, true),
                Category.newCategory("Series", null, true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var query =
                new CategorySearchQuery(expectedPage,
                        expectedPerPage,
                        expectedTerms,
                        expectedSort,
                        expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage,
                expectedPerPage,
                categories.size(),
                categories);

        final var expectedItemsCount = 2;
        final var expectedResult = expectedPagination.map(CategoryLIstOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenHasNoResult_thenReturnEmptyCategories() {
        final var categories = List.<Category>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var query =
                new CategorySearchQuery(expectedPage,
                        expectedPerPage,
                        expectedTerms,
                        expectedSort,
                        expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage,
                expectedPerPage,
                categories.size(),
                categories);

        final var expectedItemsCount = 0;
        final var expectedResult = expectedPagination.map(CategoryLIstOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_thenReturnException() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedErrorMessage = "Gateway error";

        final var query =
                new CategorySearchQuery(expectedPage,
                        expectedPerPage,
                        expectedTerms,
                        expectedSort,
                        expectedDirection);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));
        final var actualException =
                Assertions.assertThrows(IllegalStateException.class, () ->  useCase.execute(query));


        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
