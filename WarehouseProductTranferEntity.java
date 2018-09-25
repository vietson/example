package com.effect.tdb.ms.warehouseReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Admin on 7/20/2018.
 */
@Entity
public class WarehouseProductTranferEntity implements Serializable {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "delivery_no")
    private String delivery_no;
    @Column(name = "store_from")
    private String store_from;
    @Column(name = "store_to")
    private String store_to;
    @Column(name = "product_code")
    private String product_code;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "unit_delivery_name")
    private String unit_delivery_name;
    @Column(name = "input_quantity")
    private Long input_quantity;

    @Column(name = "output_quantity")
    private Long output_quantity;
    @Column(name = "difference")
    private Long difference;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDelivery_no() {
        return delivery_no;
    }

    public void setDelivery_no(String delivery_no) {
        this.delivery_no = delivery_no;
    }

    public String getStore_from() {
        return store_from;
    }

    public void setStore_from(String store_from) {
        this.store_from = store_from;
    }

    public String getStore_to() {
        return store_to;
    }

    public void setStore_to(String store_to) {
        this.store_to = store_to;
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

    public String getUnit_delivery_name() {
        return unit_delivery_name;
    }

    public void setUnit_delivery_name(String unit_delivery_name) {
        this.unit_delivery_name = unit_delivery_name;
    }

    public Long getInput_quantity() {
        return input_quantity;
    }

    public void setInput_quantity(Long input_quantity) {
        this.input_quantity = input_quantity;
    }

    public Long getOutput_quantity() {
        return output_quantity;
    }

    public void setOutput_quantity(Long output_quantity) {
        this.output_quantity = output_quantity;
    }

    public Long getDifference() {
        return difference;
    }

    public void setDifference(Long difference) {
        this.difference = difference;
    }
}
