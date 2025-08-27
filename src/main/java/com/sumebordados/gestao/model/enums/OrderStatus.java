package com.sumebordados.gestao.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    AGUARDANDO_ADIANTAMENTO("Aguardando Adiantamento"),
    ATRASADO("Atrasado"),
    EM_PRODUCAO("Em Produção"),
    CANCELADO("Cancelado"),
    FINALIZADO("Finalizado"),
    AGUARDANDO_ARTE("Aguardando Arte");

    private final String value;

    OrderStatus(String value) {
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
