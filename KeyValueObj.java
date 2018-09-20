package com.effect.tdb.bs.common;

/**
 * Created by datha on 4/20/2018.
 */
public class KeyValueObj {
    protected String key;
    protected String value;

    public KeyValueObj() {
    }

    public KeyValueObj(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValueObj{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
