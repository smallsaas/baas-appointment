package com.jfeat.am.module.appointment.services.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Code Generator
 * @since 2018-10-12
 */
@TableName("t_appointment_type")
public class AppointmentType extends Model<AppointmentType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 预约类型
     */
    private String type;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 费用
     */
    private BigDecimal fee;


    public Long getId() {
        return id;
    }

    public AppointmentType setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public AppointmentType setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AppointmentType setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public AppointmentType setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String STATUS = "status";

    public static final String FEE = "fee";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppointmentTypeItem{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", fee=" + fee +
                "}";
    }
}
