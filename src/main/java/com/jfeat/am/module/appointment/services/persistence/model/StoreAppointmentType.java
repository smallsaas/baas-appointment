package com.jfeat.am.module.appointment.services.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-10-12
 */
@TableName("t_store_appointment_type")
public class StoreAppointmentType extends Model<StoreAppointmentType> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     *  店铺ID
     */
	private Long storeId;
    /**
     * 预约项目的ID
     */
	@TableField("appointment_type_id")
	private Long appointmentTypeId;


	public Long getId() {
		return id;
	}

	public StoreAppointmentType setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getAppointmentTypeId() {
		return appointmentTypeId;
	}

	public StoreAppointmentType setAppointmentTypeId(Long appointmentTypeId) {
		this.appointmentTypeId = appointmentTypeId;
		return this;
	}

	public static final String ID = "id";

	public static final String STORE_ID = "store_id";

	public static final String APPOINTMENT_TYPE_ID = "appointment_type_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "StoreAppointmentType{" +
			"id=" + id +
			", storeId=" + storeId +
			", appointmentTypeId=" + appointmentTypeId +
			"}";
	}
}
