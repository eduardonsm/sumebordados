package com.sumebordados.gestao.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EmployeeRole {
    ADMIN("Admin"),
    EMPLOYEE("Employee");

    private String value;
    EmployeeRole(String value) {this.value = value;}

    @JsonValue
    public String getValue() {return value;}
}
