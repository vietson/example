package com.effect.tdb.ms.warehouseReport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Admin on 5/15/2018.
 */
@Entity
public class WarrehouseInvertoryReportEntity implements Serializable {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "stock_code")
    private String stock_code;
    @Column(name = "stock_name")
    private String stock_name;
    @Column(name = "product_code")
    private String product_code;
    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_type_name")
    private String product_type_name;

    @Column(name = "product_group_name")
    private String product_group_name;

    @Column(name = "productcer_name")
    private String productcer_name;

    @Column(name = "main_unit")
    private String main_unit;

    @Column(name = "opening_balance")
    private Long opening_balance;

    @Column(name = "input_period")
    private Long input_period;

    @Column(name = "output_period")
    private Long output_period;

    @Column(name = "receipt_quantity")
    private Long receipt_quantity;

    @Column(name = "lot_number")
    private String lot_number;
    @Column(name = "position")
    private String position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type_name() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name = product_type_name;
    }

    public String getProduct_group_name() {
        return product_group_name;
    }

    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    public String getProductcer_name() {
        return productcer_name;
    }

    public void setProductcer_name(String productcer_name) {
        this.productcer_name = productcer_name;
    }

    public String getMain_unit() {
        return main_unit;
    }

    public void setMain_unit(String main_unit) {
        this.main_unit = main_unit;
    }

    public Long getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(Long opening_balance) {
        this.opening_balance = opening_balance;
    }

    public Long getInput_period() {
        return input_period;
    }

    public void setInput_period(Long input_period) {
        this.input_period = input_period;
    }

    public Long getOutput_period() {
        return output_period;
    }

    public void setOutput_period(Long output_period) {
        this.output_period = output_period;
    }

    public Long getReceipt_quantity() {
        return receipt_quantity;
    }

    public void setReceipt_quantity(Long receipt_quantity) {
        this.receipt_quantity = receipt_quantity;
    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
