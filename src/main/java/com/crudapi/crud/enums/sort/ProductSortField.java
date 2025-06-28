package com.crudapi.crud.enums.sort;

import lombok.Getter;

@Getter
public enum ProductSortField {

    ID("id"),
    NAME("name"),
    PRICE("price");

    private final String sortBy;

    ProductSortField(String sortBy) {
        this.sortBy = sortBy;
    }
}
