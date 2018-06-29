package com.jfeat.am.module.appointment.services.crud.service.impl;
            
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jfeat.am.common.crud.FIELD;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
import com.jfeat.am.module.appointment.services.persistence.dao.AppointmentMapper;


import com.jfeat.am.module.appointment.services.crud.service.CRUDAppointmentService;
import com.jfeat.am.common.crud.impl.CRUDServiceOnlyImpl;
import org.springframework.stereotype.Service;
import com.jfeat.am.common.exception.BusinessCode;
import com.jfeat.am.common.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.am.common.crud.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDAppointmentService
 * @author Code Generator
 * @since 2018-06-29
 */

@Service
public class CRUDAppointmentServiceImpl  extends CRUDServiceOnlyImpl<Appointment> implements CRUDAppointmentService {





        @Resource
        private AppointmentMapper appointmentMapper;

        @Override
        protected BaseMapper<Appointment> getMasterMapper() {
                return appointmentMapper;
        }







}


