package com.jfeat.am.module.appointment.services.persistence.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;


import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Code Generator
 * @since 2018-08-29
 */
@TableName("t_appointment")
public class Appointment extends Model<Appointment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 预约码/预约成功后前天显示
     */
    private String code;
    /**
     * 预约类型
     */
    private String type;
    /**
     * 预约服务ID
     */
    @TableField("item_id")
    private Long itemId;
    /**
     * 预约店铺名称
     */
    @TableField("item_name")
    private String itemName;
    /**
     * 预约服务地址
     */
    @TableField("item_address")
    private String itemAddress;
    /**
     * 预约服务地址
     */
    @TableField("item_description")
    private String itemDescription;
    /**
     * 预约服务图标
     */
    @TableField("item_icon")
    private String itemIcon;
    /**
     * 预约客户ID
     */
    @TableField("member_id")
    private Long memberId;
    /**
     * 状态
     */
    private String status;
    /**
     * 费用
     */
    private BigDecimal fee;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 预约时间
     */
    @TableField("appointment_time")
    private Date appointmentTime;
    /**
     * 结束时间
     */
    @TableField("close_time")
    private Date closeTime;
    /**
     * 预约客户电话
     */
    @TableField("member_phone")
    private String memberPhone;
    /**
     * 预约客户名称
     */
    @TableField("member_name")
    private String memberName;
    /**
     * 接待员ID
     */
    @TableField("receptionist_id")
    private Long receptionistId;
    /**
     * 处理员ID
     */
    @TableField("server_id")
    private Long serverId;
    /**
     * 处理员
     */
    @TableField("server_name")
    private String serverName;
    /**
     * 接待员
     */
    @TableField("receptionist_name")
    private String receptionistName;
    /**
     * 支付时间
     */
    @TableField("payment_timestamp")
    private Date paymentTimestamp;
    /**
     * 支付方试
     */
    @TableField("payment_method")
    private String paymentMethod;
    /**
     * 保留字段
     */
    @TableField("field_c")
    private String fieldC;

    /**
     * 预约最早时间
     */
    @TableField("earliest_time")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date earliestTime;


    /**
     * 预约最迟时间
     */
    @TableField("latest_time")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date latestTime;

    /**
     * 预约客户用户ID
     */
    @TableField("user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public Appointment setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Appointment setCode(String code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public Appointment setType(String type) {
        this.type = type;
        return this;
    }

    public Long getItemId() {
        return itemId;
    }

    public Appointment setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public Appointment setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getItemAddress() {
        return itemAddress;
    }

    public Appointment setItemAddress(String itemAddress) {
        this.itemAddress = itemAddress;
        return this;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Appointment setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public Appointment setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Appointment setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Appointment setStatus(String status) {
        this.status = status;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Appointment setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Appointment setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public Appointment setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
        return this;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public Appointment setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public Appointment setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
        return this;
    }

    public String getMemberName() {
        return memberName;
    }

    public Appointment setMemberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public Long getReceptionistId() {
        return receptionistId;
    }

    public Appointment setReceptionistId(Long receptionistId) {
        this.receptionistId = receptionistId;
        return this;
    }

    public Long getServerId() {
        return serverId;
    }

    public Appointment setServerId(Long serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public Appointment setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public Appointment setReceptionistName(String receptionistName) {
        this.receptionistName = receptionistName;
        return this;
    }

    public Date getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public Appointment setPaymentTimestamp(Date paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Appointment setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getFieldC() {
        return fieldC;
    }

    public Appointment setFieldC(String fieldC) {
        this.fieldC = fieldC;
        return this;
    }

    public Date getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(Date earliestTime) {
        this.earliestTime = earliestTime;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static final String ID = "id";

    public static final String USERID = "user_id";

    public static final String CODE = "code";

    public static final String TYPE = "type";

    public static final String ITEM_ID = "item_id";

    public static final String ITEM_NAME = "item_name";

    public static final String ITEM_ADDRESS = "item_address";

    public static final String ITEM_DESCRIPTION = "item_description";

    public static final String ITEM_ICON = "item_icon";

    public static final String MEMBER_ID = "member_id";

    public static final String STATUS = "status";

    public static final String FEE = "fee";

    public static final String CREATE_TIME = "create_time";

    public static final String APPOINTMENT_TIME = "appointment_time";

    public static final String CLOSE_TIME = "close_time";

    public static final String MEMBER_PHONE = "member_phone";

    public static final String MEMBER_NAME = "member_name";

    public static final String RECEPTIONIST_ID = "receptionist_id";

    public static final String SERVER_ID = "server_id";

    public static final String SERVER_NAME = "server_name";

    public static final String RECEPTIONIST_NAME = "receptionist_name";

    public static final String PAYMENT_TIMESTAMP = "payment_timestamp";

    public static final String PAYMENT_METHOD = "payment_method";

    public static final String FIELD_C = "field_c";

    public static final String EARLIEST_TIME = "earliest_time";

    public static final String LATEST_TIME = "latest_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                "userId=" + userId +
                ", code=" + code +
                ", type=" + type +
                ", itemId=" + itemId +
                ", itemName=" + itemName +
                ", itemAddress=" + itemAddress +
                ", itemDescription=" + itemDescription +
                ", itemIcon=" + itemIcon +
                ", memberId=" + memberId +
                ", status=" + status +
                ", fee=" + fee +
                ", createTime=" + createTime +
                ", appointmentTime=" + appointmentTime +
                ", closeTime=" + closeTime +
                ", memberPhone=" + memberPhone +
                ", memberName=" + memberName +
                ", receptionistId=" + receptionistId +
                ", serverId=" + serverId +
                ", serverName=" + serverName +
                ", receptionistName=" + receptionistName +
                ", paymentTimestamp=" + paymentTimestamp +
                ", paymentMethod=" + paymentMethod +
                ", fieldC=" + fieldC +
                ", earliestTime=" + earliestTime +
                ", latestTime=" + latestTime +
                "}";
    }
}
