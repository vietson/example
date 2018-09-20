package com.effect.tdb.bs.common;

/**
 * Created by datha on 5/7/2018.
 */
public class ServiceResponse {

    private ServiceResponseStatus status;
    private Object data;

    public ServiceResponse(ServiceResponseStatus status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ServiceResponse() {
    }

    public ServiceResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
