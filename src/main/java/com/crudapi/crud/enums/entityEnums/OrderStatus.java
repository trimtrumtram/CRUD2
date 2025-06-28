package com.crudapi.crud.enums.entityEnums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    NEW("Новый заказ"),
    PROCESSING("Выполняется"),
    COMPLETED("Выполнен"),
    CANCELLED("Отменен");

    private final String russianDescription;
}
