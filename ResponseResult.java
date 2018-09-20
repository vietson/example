package com.effect.tdb.bs.common;


/**
 * Created by Admin on 4/11/2018.
 */
public class ResponseResult<T> {
    public Boolean success;
    public String exceptionId;
    public String exceptionMessage;
    public T dataResponse;

    public ResponseResult() {
    }

    public ResponseResult(Boolean success, String exceptionId, String exceptionMessage, T dataResponse) {
        this.success = success;
        this.exceptionId = exceptionId;
        this.exceptionMessage = exceptionMessage;
        this.dataResponse = dataResponse;
    }

    public ResponseResult(T dataResponse) {
        this.success = true;
        this.dataResponse = dataResponse;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public T getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(T dataResponse) {
        this.dataResponse = dataResponse;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
