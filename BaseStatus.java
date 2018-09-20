package com.effect.tdb.bs.common;

/**
 * Created by datha on 7/4/2018.
 */
public enum BaseStatus {
    DB_ROW_ACTIVE("1"), DB_ROW_INACTIVE("0");

    private String value;
    private String name;

    private BaseStatus(String key, String name) {
        this.value = key;
        this.name = name;
    }

    private BaseStatus(String key) {
        this.value = key;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
