package com.jfeat.am.module.appointment.services.domain.definition;

/**
 * Created by Administrator on 2017/9/18.
 */
public enum AppointmentStatus {

    PAY_PENDING,        //待付款
    PAY_TIMEOUT,        //取消支付
    //PAYMENT_CANCELLED,  //未支付=待付款
    WAIT_TO_STORE,      //待到店 - ok
    ALREADY_TO_STORE,   //已到店 - met
    MISS_TO_STORE,      //失约未到店 - missed
    //NO_TO_STORE,      //未到店=待到店
    CANCELLED,           //用户已取消 - cancelled
    EXPIRED             //过期未处理 [查询同时处理过期状态]
}
