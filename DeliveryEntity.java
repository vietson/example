package com.effect.tdb.ms.delivery;

import com.effect.tdb.ms.deliverynote.DeliveryNoteEntity;
import com.effect.tdb.ms.dictionary.StockEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import static com.effect.tdb.ms.common.Constants.DATE_TIME_FORMART_CLIENT;

/**
 * Created by Admin on 08/05/2018.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "delivery", catalog = "")
public class DeliveryEntity {
    private int id;
    private String deliveryNo;
    private Timestamp deliveryDate;
    private String deliveryPersonId;
    private Integer deliveryNoteId;
    private Integer stockId;
    private String deliveryTypeId;
    private String description;
    private Byte status;
    private List<DeliveryProductEntity> deliveryProductsById;
    private DeliveryNoteEntity deliveryNoteByDeliveryNoteId;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String invoiceNo;
    private Integer customerId;

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
    @Column(name = "delivery_no")
    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Basic
    @JsonFormat(pattern = DATE_TIME_FORMART_CLIENT)
    @Column(name = "delivery_date")
    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Basic
    @Column(name = "delivery_person_id")
    public String getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(String deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    @Basic
    @Column(name = "delivery_note_id")
    public Integer getDeliveryNoteId() {
        return deliveryNoteId;
    }

    public void setDeliveryNoteId(Integer deliveryNoteId) {
        this.deliveryNoteId = deliveryNoteId;
    }

    @Basic
    @Column(name = "stock_id")
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Basic
    @Column(name = "delivery_type_id")
    public String getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public void setDeliveryTypeId(String deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryEntity that = (DeliveryEntity) o;

        if (id != that.id) return false;
        if (deliveryNo != null ? !deliveryNo.equals(that.deliveryNo) : that.deliveryNo != null) return false;
        if (deliveryDate != null ? !deliveryDate.equals(that.deliveryDate) : that.deliveryDate != null) return false;
        if (deliveryPersonId != null ? !deliveryPersonId.equals(that.deliveryPersonId) : that.deliveryPersonId != null)
            return false;
        if (deliveryNoteId != null ? !deliveryNoteId.equals(that.deliveryNoteId) : that.deliveryNoteId != null)
            return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (deliveryTypeId != null ? !deliveryTypeId.equals(that.deliveryTypeId) : that.deliveryTypeId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (deliveryNo != null ? deliveryNo.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (deliveryPersonId != null ? deliveryPersonId.hashCode() : 0);
        result = 31 * result + (deliveryNoteId != null ? deliveryNoteId.hashCode() : 0);
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        result = 31 * result + (deliveryTypeId != null ? deliveryTypeId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "deliveryByDeliveryId", cascade = CascadeType.ALL)
    public List<DeliveryProductEntity> getDeliveryProductsById() {
        return deliveryProductsById;
    }

    public void setDeliveryProductsById(List<DeliveryProductEntity> deliveryProductsById) {
        this.deliveryProductsById = deliveryProductsById;
    }

    @ManyToOne
    @JoinColumn(name = "delivery_note_id", referencedColumnName = "id", insertable = false, updatable = false)
    public DeliveryNoteEntity getDeliveryNoteByDeliveryNoteId() {
        return deliveryNoteByDeliveryNoteId;
    }

    public void setDeliveryNoteByDeliveryNoteId(DeliveryNoteEntity deliveryNoteByDeliveryNoteId) {
        this.deliveryNoteByDeliveryNoteId = deliveryNoteByDeliveryNoteId;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "invoice_no")
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    private StockEntity stock;

    @ManyToOne(targetEntity = StockEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id", insertable = false, updatable = false)
    public StockEntity getStock() {
        return stock;
    }

    public void setStock(StockEntity stock) {
        this.stock = stock;
    }
}