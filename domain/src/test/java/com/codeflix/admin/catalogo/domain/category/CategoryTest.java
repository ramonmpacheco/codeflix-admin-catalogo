package com.codeflix.admin.catalogo.domain.category;


import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateANewOne() {
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDesc, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.getActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var category = Category.newCategory(null, expectedDesc, expectedIsActive);
        final var ex = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = " ";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);
        final var ex = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Ra ";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);
        final var ex = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthGreaterThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = """
                Fala pro cliente que o deploy automatizado no Heroku causou o bug dos argumentos
                que definem um schema dinâmico. Com este commit, o gerenciador de dependências do frontend causou o 
                bug do nosso servidor de DNS. Dado o fluxo de dados atual, o módulo de recursão paralela causou o 
                bug da execução de requisições eficientes na API.
                """;

        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);
        final var ex = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, ex.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, ex.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategory_thenInstantiateANewOne() {
        final var expectedName = "Filmes";
        final var expectedDesc = "";
        final var expectedIsActive = true;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDesc, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.getActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategory_thenInstantiateANewOne() {
        final var expectedName = "Filmes";
        final var expectedDesc = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var category = Category.newCategory(expectedName, expectedDesc, expectedIsActive);
        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDesc, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.getActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }


}