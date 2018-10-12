package com.jfeat.am.module.appointment.services.domain.dao;


import com.jfeat.am.module.appointment.services.persistence.model.AppointmentType;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Code Generator on 2018-06-29
 */
public interface QueryAppointmentTypeDao {

    List<AppointmentType> findAppointmentType(@Param("storeId")Long storeId);
}