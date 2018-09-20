package com.effect.tdb.ms.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by datha on 5/4/2018.
 */
@Entity
@Table(name = "product_package", catalog = "")
public class ProductPackageEntity {
    private int id;
    private String code;
    private String name;
    private int standardQuantity;
    private double price;
    private String updatedPerson;
    private Timestamp updatedDate;
    private Timestamp createdDate;
    private ProductEntity productByIdProduct;
    private UnitEntity unitByIdUnit;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "standard_quantity")
    public int getStandardQuantity() {
        return standardQuantity;
    }

    public void setStandardQuantity(int standardQuantity) {
        this.standardQuantity = standardQuantity;
    }

    @Basic
    @Column(name = "updated_person")
    public String getUpdatedPerson() {
        return updatedPerson;
    }

    public void setUpdatedPerson(String updatedPerson) {
        this.updatedPerson = updatedPerson;
    }

    @Basic
    @Column(name = "updated_date")
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Basic
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPackageEntity that = (ProductPackageEntity) o;

        if (id != that.id) return false;
        if (standardQuantity != that.standardQuantity) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (updatedPerson != null ? !updatedPerson.equals(that.updatedPerson) : that.updatedPerson != null)
            return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + standardQuantity;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (updatedPerson != null ? updatedPerson.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @JsonProperty(value = "productByIdProduct")
    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    public ProductEntity getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(ProductEntity productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }

    @ManyToOne
    @JoinColumn(name = "id_unit", referencedColumnName = "id")
    public UnitEntity getUnitByIdUnit() {
        return unitByIdUnit;
    }

    public void setUnitByIdUnit(UnitEntity unitByIdUnit) {
        this.unitByIdUnit = unitByIdUnit;
    }
}
