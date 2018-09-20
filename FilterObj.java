package com.effect.tdb.bs.common;

/**
 * Created by datha on 6/26/2018.
 */
public class FilterObj extends KeyValueObj {
    public FilterObj(String key, String value, FilterType type, String filterValue) {
        super(key, value);
        this.type = type;
        this.filterValue = filterValue;
    }

    public FilterObj(String key, String value, FilterType type) {
        super(key, value);
        this.type = type;
    }

    private FilterType type;

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    private String filterValue;

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}
