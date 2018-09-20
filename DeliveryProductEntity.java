package com.effect.tdb.ms.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static com.effect.tdb.ms.common.Constants.DATE_TIME_FORMART_CLIENT;

/**
 * Created by Admin on 08/05/2018.
 */
@Entity
@Table(name = "delivery_product", catalog = "")
public class DeliveryProductEntity {
    private int id;
    private Integer productId;
    private Integer requestQuanty;
    private Integer realQty;
    private String lotNumber;
    private Integer position;
    private DeliveryEntity deliveryByDeliveryId;
    private Integer productUnitId;
    private Integer realConvertQty;
    private Integer productUnitDeliveryId;
    private Integer requestConvertQty;
    private Integer remainConvertQty;
    private Integer deliveryNoteProductId;
    private Timestamp expireDate;
    private Double price;
    private Integer amount;
    private Integer discount;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_id")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "request_quanty")
    public Integer getRequestQuanty() {
        return requestQuanty;
    }

    public void setRequestQuanty(Integer requestQuanty) {
        this.requestQuanty = requestQuanty;
    }

    @Basic
    @Column(name = "real_qty")
    public Integer getRealQty() {
        return realQty;
    }

    public void setRealQty(Integer realQty) {
        this.realQty = realQty;
    }

    @Basic
    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Basic
    @Column(name = "product_unit_id")
    public Integer getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(Integer productUnitId) {
        this.productUnitId = productUnitId;
    }

    @Basic
    @Column(name = "lot_number")
    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryProductEntity that = (DeliveryProductEntity) o;

        if (id != that.id) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (requestQuanty != null ? !requestQuanty.equals(that.requestQuanty) : that.requestQuanty != null) return false;
        if (realConvertQty != null ? !realConvertQty.equals(that.realConvertQty) : that.realConvertQty != null) return false;
        if (realQty != null ? !realQty.equals(that.realQty) : that.realQty != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (productUnitId != null ? !productUnitId.equals(that.productUnitId) : that.productUnitId != null) return false;
        if (lotNumber != null ? !lotNumber.equals(that.lotNumber) : that.lotNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (requestQuanty != null ? requestQuanty.hashCode() : 0);
        result = 31 * result + (realQty != null ? realQty.hashCode() : 0);
        result = 31 * result + (realConvertQty != null ? realConvertQty.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (productUnitId != null ? productUnitId.hashCode() : 0);
        result = 31 * result + (lotNumber != null ? lotNumber.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @JsonProperty(value="deliveryByDeliveryId")
    @ManyToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    public DeliveryEntity getDeliveryByDeliveryId() {
        return deliveryByDeliveryId;
    }

    public void setDeliveryByDeliveryId(DeliveryEntity deliveryByDeliveryId) {
        this.deliveryByDeliveryId = deliveryByDeliveryId;
    }

    @Basic
    @Column(name = "real_convert_qty")
    public Integer getRealConvertQty() {
        return realConvertQty;
    }

    public void setRealConvertQty(Integer realConvertQty) {
        this.realConvertQty = realConvertQty;
    }

    @Basic
    @Column(name = "product_unit_delivery_id")
    public Integer getProductUnitDeliveryId() {
        return productUnitDeliveryId;
    }

    public void setProductUnitDeliveryId(Integer productUnitDeliveryId) {
        this.productUnitDeliveryId = productUnitDeliveryId;
    }

    @Basic
    @Column(name = "request_convert_qty")
    public Integer getRequestConvertQty() {
        return requestConvertQty;
    }

    public void setRequestConvertQty(Integer requestConvertQty) {
        this.requestConvertQty = requestConvertQty;
    }

    @Basic
    @Column(name = "remain_convert_qty")
    public Integer getRemainConvertQty() {
        return remainConvertQty;
    }

    public void setRemainConvertQty(Integer remainConvertQty) {
        this.remainConvertQty = remainConvertQty;
    }

    @Basic
    @Column(name = "delivery_note_product_id")
    public Integer getDeliveryNoteProductId() {
        return deliveryNoteProductId;
    }

    public void setDeliveryNoteProductId(Integer deliveryNoteProductId) {
        this.deliveryNoteProductId = deliveryNoteProductId;
    }

    @Basic
    @JsonFormat(pattern = DATE_TIME_FORMART_CLIENT)
    @Column(name = "expire_date")
    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }

    @Basic
    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "discount")
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
