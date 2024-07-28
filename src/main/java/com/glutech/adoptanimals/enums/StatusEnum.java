package com.glutech.adoptanimals.enums;

public enum StatusEnum {
    AVAILABLE("available"),
    ADOPTED("adopted"),
    ;

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
