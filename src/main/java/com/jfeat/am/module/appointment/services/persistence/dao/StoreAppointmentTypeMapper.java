package com.jfeat.am.module.appointment.services.persistence.dao;

import com.jfeat.am.module.appointment.services.persistence.model.StoreAppointmentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Code Generator
 * @since 2018-10-12
 */
public interface StoreAppointmentTypeMapper extends BaseMapper<StoreAppointmentType> {

    StoreAppointmentType selectOne(StoreAppointmentType relation);
}