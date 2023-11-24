package com.codeflix.admin.catalogo.domain.exceptions;

import java.util.List;

import com.codeflix.admin.catalogo.domain.validation.Error;

public class DomainException extends NoStacktraceException{
    private final List<Error> errs;

    protected DomainException(final String message, final List<Error> errs) {
        super(message);
        this.errs =  errs;
    }

    public static DomainException with(final Error err) {
        return new DomainException(err.message(), List.of(err));
    }

    public static DomainException with(final List<Error> errs) {
        return new DomainException("", errs);
    }

    public List<Error> getErrors() {
        return errs;
    }
}
