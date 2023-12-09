package com.codeflix.admin.catalogo.domain.validation.handler;

import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.domain.validation.Error;
import com.codeflix.admin.catalogo.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(Error err) {
        throw DomainException.with(err);
    }

    @Override
    public ValidationHandler append(ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public <T> T validate(Validation<T> validation) {
        try {
            return validation.validate();
        }catch (final Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
