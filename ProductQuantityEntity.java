package com.effect.tdb.ms.delivery;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Admin on 7/19/2018.
 */
@Entity
public class ProductQuantityEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "store_id")
    private int storeId;
    @Column(name = "remain_quantity_convert")
    private Integer remainQuantityConvert;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRemainQuantityConvert() {
        return remainQuantityConvert;
    }

    public void setRemainQuantityConvert(Integer remainQuantityConvert) {
        this.remainQuantityConvert = remainQuantityConvert;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
