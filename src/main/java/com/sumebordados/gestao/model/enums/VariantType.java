package com.sumebordados.gestao.model.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VariantType {
    NORMAL("Normal"),
    BABYLOOK("Babylook"),
    MANGA_LONGA("Manga Longa");

    private final String value;

    VariantType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}