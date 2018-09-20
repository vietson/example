package com.effect.tdb.bs.common;

/**
 * Created by datha on 6/26/2018.
 */
public enum FilterType {
    EQUAL(0),
    LIKE(1),
    AND(2),
    OR(3),
    GREATER_THAN_OR_EQUAL_DATE(4),
    LOWER_THAN_OR_EQUAL_DATE(5),
    GREATER(6),
    LOWER(7),
    IN(8);
    private int value;

    private FilterType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FilterType getByValue(int value) {
        for (FilterType filterType : FilterType.values()) {
            if (filterType.value == value) {
                return filterType;
            }
        }
        return null;
    }
}
