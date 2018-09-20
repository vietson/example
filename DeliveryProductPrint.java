package com.effect.tdb.ms.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import static com.effect.tdb.ms.common.Constants.DATE_TIME_FORMART_CLIENT;

/**
 * Persistent accounts entity with JPA markup. Accounts are stored in an H2
 * relational database.
 *
 * @author Paul Chapman
 */
@Entity
public class DeliveryProductPrint implements Serializable {

    private String code;

    private String name;

    private String storename;

    private String unitname;

    private Integer inputqty;

    private String lotNumber;

    private Timestamp expiredate;

    @Id
    private int id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storename;
    }

    public void setStoreName(String storeName) {
        this.storename = storeName;
    }

    public String getUnitName() {
        return unitname;
    }

    public void setUnitName(String unitName) {
        this.unitname = unitName;
    }

    public Integer getInputQty() {
        return inputqty;
    }

    public void setInputQty(Integer inputQty) {
        this.inputqty = inputQty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @JsonFormat(pattern = DATE_TIME_FORMART_CLIENT)
    public Timestamp getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Timestamp expiredate) {
        this.expiredate = expiredate;
    }
}
