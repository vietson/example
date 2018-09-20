package com.effect.tdb.bs.common;

import java.util.List;

/**
 * Created by Admin on 4/12/2018.
 */
public class ResponseRequest<T, T1> {
    private T dataResponse;
    private List<T1> listAdd;
    private List<T1> listEdit;
    private List<T1> listDelete;

    public T getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(T dataResponse) {
        this.dataResponse = dataResponse;
    }

    public List<T1> getListAdd() {
        return listAdd;
    }

    public void setListAdd(List<T1> listAdd) {
        this.listAdd = listAdd;
    }

    public List<T1> getListEdit() {
        return listEdit;
    }

    public void setListEdit(List<T1> listEdit) {
        this.listEdit = listEdit;
    }

    public List<T1> getListDelete() {
        return listDelete;
    }

    public void setListDelete(List<T1> listDelete) {
        this.listDelete = listDelete;
    }
}
