package com.codeflix.admin.catalogo.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN in);
}