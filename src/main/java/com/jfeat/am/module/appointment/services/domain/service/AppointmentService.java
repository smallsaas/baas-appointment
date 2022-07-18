package com.jfeat.am.module.appointment.services.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.appointment.services.crud.service.CRUDAppointmentService;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;

import java.util.Date;
import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface AppointmentService extends CRUDAppointmentService{

    /**
     * 我的预约列表
     * */
    List<Appointment> myAppointments(Page<Appointment> page, Long memberId, String status);

    /**
     * 我的店铺预约列表
     */
    List<Appointment> myBusinessAppointments(Page<Appointment> page, Long itemId, String status, Integer isAssigned, String doneSituation, String type, String search, Date startTime, Date endTime);
}