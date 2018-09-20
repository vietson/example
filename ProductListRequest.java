package com.effect.tdb.ms.delivery;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 5/9/2018.
 */
public class ProductListRequest implements Serializable {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

}
