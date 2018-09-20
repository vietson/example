package com.effect.tdb.bs.common;


/**
 * Created by datha on 6/29/2018.
 */
public class FilterObject {
    private String fieldName;
    private String value;
    private FilterType expression;
    private FilterType operator;

    public FilterObject(String fieldName, String value, FilterType expression, FilterType operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.expression = expression;
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FilterType getExpression() {
        return expression;
    }

    public void setExpression(FilterType expression) {
        this.expression = expression;
    }

    public FilterType getOperator() {
        return operator;
    }

    public void setOperator(FilterType operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "FilterObject{" +
                "fieldName='" + fieldName + '\'' +
                ", value='" + value + '\'' +
                ", expression=" + expression +
                ", operator=" + operator +
                '}';
    }
}
