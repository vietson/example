package com.effect.tdb.ms.delivery;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by datha on 5/4/2018.
 */
@Entity
@Table(name = "product", catalog = "")
public class ProductEntity {
    private int id;
    private String oldCode;
    private String code;
    private String name;
    private String barcode;
    private Integer idMaterial;
    private Integer idProducer;
    private Integer idCountry;
    private String importer;
    private String ingredient;
    private String virtue;
    private String description;
    private Integer status;
    private String updatedPerson;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String distributor;
    private String license;
    private Timestamp expireDate;
    private Collection<ProductPackageEntity> productPackagesById;
    private ProductGroupEntity groupByIdGroup;
    private ProductTypeEntity typeByIdType;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "oldcode")
    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
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
    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Basic
    @Column(name = "id_material")
    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    @Basic
    @Column(name = "id_producer")
    public Integer getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(Integer idProducer) {
        this.idProducer = idProducer;
    }

    @Basic
    @Column(name = "id_country")
    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    @Basic
    @Column(name = "importer")
    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    @Basic
    @Column(name = "ingredient")
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Basic
    @Column(name = "virtue")
    public String getVirtue() {
        return virtue;
    }

    public void setVirtue(String virtue) {
        this.virtue = virtue;
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
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
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
    @Column(name = "distributor")
    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    @Basic
    @Column(name = "license")
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Basic
    @Column(name = "expiredate")
    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (id != that.id) return false;
        if (oldCode != null ? !oldCode.equals(that.oldCode) : that.oldCode != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (barcode != null ? !barcode.equals(that.barcode) : that.barcode != null) return false;
        if (idMaterial != null ? !idMaterial.equals(that.idMaterial) : that.idMaterial != null) return false;
        if (idProducer != null ? !idProducer.equals(that.idProducer) : that.idProducer != null) return false;
        if (idCountry != null ? !idCountry.equals(that.idCountry) : that.idCountry != null) return false;
        if (importer != null ? !importer.equals(that.importer) : that.importer != null) return false;
        if (ingredient != null ? !ingredient.equals(that.ingredient) : that.ingredient != null) return false;
        if (virtue != null ? !virtue.equals(that.virtue) : that.virtue != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (updatedPerson != null ? !updatedPerson.equals(that.updatedPerson) : that.updatedPerson != null)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (distributor != null ? !distributor.equals(that.distributor) : that.distributor != null) return false;
        if (license != null ? !license.equals(that.license) : that.license != null) return false;
        if (expireDate != null ? !expireDate.equals(that.expireDate) : that.expireDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (oldCode != null ? oldCode.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (idMaterial != null ? idMaterial.hashCode() : 0);
        result = 31 * result + (idProducer != null ? idProducer.hashCode() : 0);
        result = 31 * result + (idCountry != null ? idCountry.hashCode() : 0);
        result = 31 * result + (importer != null ? importer.hashCode() : 0);
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        result = 31 * result + (virtue != null ? virtue.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updatedPerson != null ? updatedPerson.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (distributor != null ? distributor.hashCode() : 0);
        result = 31 * result + (license != null ? license.hashCode() : 0);
        result = 31 * result + (expireDate != null ? expireDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "productByIdProduct")
    public Collection<ProductPackageEntity> getProductPackagesById() {
        return productPackagesById;
    }

    public void setProductPackagesById(Collection<ProductPackageEntity> productPackagesById) {
        this.productPackagesById = productPackagesById;
    }

    @ManyToOne
    @JoinColumn(name = "id_product_group", referencedColumnName = "id")
    public ProductGroupEntity getGroupByIdGroup() {
        return groupByIdGroup;
    }

    public void setGroupByIdGroup(ProductGroupEntity groupByIdGroup) {
        this.groupByIdGroup = groupByIdGroup;
    }

    @ManyToOne
    @JoinColumn(name = "id_product_type", referencedColumnName = "id")
    public ProductTypeEntity getTypeByIdType() {
        return typeByIdType;
    }

    public void setTypeByIdType(ProductTypeEntity typeByIdType) {
        this.typeByIdType = typeByIdType;
    }
}
