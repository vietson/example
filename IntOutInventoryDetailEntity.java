package com.effect.tdb.ms.warehouseReport;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Admin on 5/15/2018.
 */
@Entity
public class IntOutInventoryDetailEntity implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "stock_code")
    private String stock_code;

    @Column(name = "stock_name")
    private String stock_name;

    @Column(name = "product_code")
    private String product_code;

    @Column(name = "execute_code")
    private String execute_code;
    @Column(name = "execute_date")
    private String execute_date;
    @Column(name = "position")
    private String position;

    @Column(name = "description")
    private String description;
    @Column(name = "producer_name")
    private String producer_name;
    @Column(name = "product_name")
    private String product_name;


    @Column(name = "product_type_name")
    private String product_type_name;
    @Column(name = "product_group_name")
    private String product_group_name;
    @Column(name = "unit_name")
    private String unit_name;
    @Column(name = "lot_number")
    private String lot_number;


    @Column(name = "input_quanity")
    private Long input_quanity;
    @Column(name = "out_quantity")
    private Long out_quantity;
    @Column(name = "quantity_convert")
    private Long quantity_convert;

    @Column(name = "customer_name")
    private String customer_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExecute_code() {
        return execute_code;
    }

    public void setExecute_code(String execute_code) {
        this.execute_code = execute_code;
    }

    public String getExecute_date() {
        return execute_date;
    }

    public void setExecute_date(String execute_date) {
        this.execute_date = execute_date;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer_name() {
        return producer_name;
    }

    public void setProducer_name(String producer_name) {
        this.producer_name = producer_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
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

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public Long getInput_quanity() {
        return input_quanity;
    }

    public void setInput_quanity(Long input_quanity) {
        this.input_quanity = input_quanity;
    }

    public Long getOut_quantity() {
        return out_quantity;
    }

    public void setOut_quantity(Long out_quantity) {
        this.out_quantity = out_quantity;
    }

    public Long getQuantity_convert() {
        return quantity_convert;
    }

    public void setQuantity_convert(Long quantity_convert) {
        this.quantity_convert = quantity_convert;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
