package com.codeflix.admin.catalogo.infrastructure.api.controllers;

import com.codeflix.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.codeflix.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.codeflix.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.codeflix.admin.catalogo.application.category.retrieve.list.ListCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.update.UpdateCategoryCommand;
import com.codeflix.admin.catalogo.application.category.update.UpdateCategoryOutput;
import com.codeflix.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.codeflix.admin.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import com.codeflix.admin.catalogo.domain.validation.handler.Notification;
import com.codeflix.admin.catalogo.infrastructure.api.CategoryAPI;
import com.codeflix.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import com.codeflix.admin.catalogo.infrastructure.category.models.CategoryResponse;
import com.codeflix.admin.catalogo.infrastructure.category.models.CreateCategoryRequest;
import com.codeflix.admin.catalogo.infrastructure.category.models.UpdateCategoryRequest;
import com.codeflix.admin.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoryUseCase listCategoryUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoryUseCase listCategoryUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoryUseCase = Objects.requireNonNull(listCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {
        final var command = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);
        return this.createCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(String search, int page, int perPage, String sort, String direction) {
        return listCategoryUseCase.execute(new CategorySearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(final String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
        final var command = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;
        return this.updateCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String id) {
        this.deleteCategoryUseCase.execute(id);
    }
}
