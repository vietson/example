package com.effect.tdb.ms.delivery;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 22/05/2018.
 */
public class ListProductEntity implements Serializable{
    List<ProductEntity> listProductEntity;

    public List<ProductEntity> getListProductEntity() {
        return listProductEntity;
    }

    public void setListProductEntity(List<ProductEntity> listProductEntity) {
        this.listProductEntity = listProductEntity;
    }
}
