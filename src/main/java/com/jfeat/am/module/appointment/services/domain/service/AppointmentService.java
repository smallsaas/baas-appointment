package com.jfeat.am.module.appointment.services.domain.service;

import com.jfeat.am.module.appointment.services.crud.service.CRUDAppointmentService;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface AppointmentService extends CRUDAppointmentService{



    /**
     * 我的预约列表
     * */
    List<Appointment> myAppointments(long memberId);
}