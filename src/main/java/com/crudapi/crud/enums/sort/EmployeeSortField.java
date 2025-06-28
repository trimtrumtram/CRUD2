package com.crudapi.crud.enums.sort;

import lombok.Getter;

@Getter
public enum EmployeeSortField {

    ID("id"),
    FIRSTNAME("firstName"),
    LASTNAME("lastName"),
    ROLE("role"),
    EMAIL("email");

    private final String sortBy;

    EmployeeSortField(String sortBy) {
        this.sortBy = sortBy;
    }
}
