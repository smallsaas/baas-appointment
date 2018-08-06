package com.jfeat.am.module.appointment.services.domain.definition;

/**
 * Created by Administrator on 2017/9/18.
 */
public enum AppointmentStatus {

    PAY_PENDING,    //待支付
    PAY_TIMEOUT,    //支付超时
    WAIT_TO_STORE,  //待到店
    PAYMENT_CANCEL, //未支付
    ALREADY_TO_STORE,   //已到店
    NO_TO_STORE,    //未到店
    CANCEL          //已取消

}
