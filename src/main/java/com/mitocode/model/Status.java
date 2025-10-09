package com.mitocode.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    //ACTIVE,
    //INACTIVE

    //-- Con el uso del converter --//
    ACTIVE("A"),
    INACTIVE("I");

    private final String dbValue;
    Status(String dbValue) { this.dbValue = dbValue;}
    public String getDbValue() { return this.dbValue; }

    // LECTURA | GET
    public static Status fromDbValue(String v) {
        if (v == null) return null;
        return switch (v) {
            case "A" -> ACTIVE;
            case "I" -> INACTIVE;
            default -> throw new IllegalArgumentException("Unknown database value: " + v);
        };
    }

    // ESCRITURA | POST, PUT
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Status fromJson(String raw) {
        if (raw == null) return null;
        String v = raw.trim().toUpperCase();
        return switch (v) {
            case "A", "ACTIVE" -> ACTIVE;
            case "I", "INACTIVE" -> INACTIVE;
            default -> throw new IllegalArgumentException("Unknown value: " + raw);
        };
    }
}
