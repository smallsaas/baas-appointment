package com.jfeat.am.module.appointment.services.domain.dao;

import com.jfeat.am.module.appointment.services.domain.model.AppointmentRecord;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by Code Generator on 2018-06-29
 */
public interface QueryAppointmentDao extends BaseMapper<AppointmentRecord> {
    List<AppointmentRecord> findAppointmentPage(Page<AppointmentRecord> page, @Param("record") AppointmentRecord record, @Param("orderBy") String orderBy);
}