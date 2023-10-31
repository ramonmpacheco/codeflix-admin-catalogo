package com.codeflix.admin.catalogo.domain.category;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID>{
    public AggregateRoot(final ID id) {
        super(id);
    }

}
