package ru.devpro.enums;


import lombok.Getter;

@Getter
public enum State {

        AWAITING_ADDRESS,
        AWAITING_CITY,
        AWAITING_STATE,
        AWAITING_ZIPCODE,
        AWAITING_NAME,
        AWAITING_SAFETY,


        // Другие состояния для разных категорий данных
    }

