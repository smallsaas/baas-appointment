package com.jfeat.am.module.appointment.api.crud;

import java.util.Date;

/**
 * Created by vincenthuang on 28/08/2018.
 */
public class PaymentRequest {
    private Date timestamp;
    private String method;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
