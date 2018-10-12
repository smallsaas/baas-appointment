package com.jfeat.am.module.appointment.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.appointment.services.persistence.model.AppointmentType;

import java.util.List;

public interface AppointmentTypeService {



    public Integer createAppointmentType(AppointmentType entity);


    public Integer updateAppointmentType(Long id,AppointmentType entity);


    public Integer deleteAppointmentType(Long id);


    public List<AppointmentType> appointmentType(Page<AppointmentType> page, String name);


    public List<AppointmentType> findAppointmentType(Long storeId);
}
