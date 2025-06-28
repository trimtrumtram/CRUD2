package com.crudapi.crud.enums.sort;

import lombok.Getter;

@Getter
public enum OrderSortField {

    CREATION_DATE_TIME("creationDateTime"),
    START_DATE("startDate"),
    END_DATE("endDate");

    private final String sortBy;

    OrderSortField(String sortBy) {
        this.sortBy = sortBy;
    }
}
