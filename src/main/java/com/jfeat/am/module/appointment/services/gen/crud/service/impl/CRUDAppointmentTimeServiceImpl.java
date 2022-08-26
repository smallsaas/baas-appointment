package com.jfeat.am.module.appointment.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.appointment.services.gen.persistence.model.AppointmentTime;
import com.jfeat.am.module.appointment.services.gen.persistence.dao.AppointmentTimeMapper;
import com.jfeat.am.module.appointment.services.gen.crud.service.CRUDAppointmentTimeService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDAppointmentTimeService
 * @author Code generator
 * @since 2022-08-26
 */

@Service
public class CRUDAppointmentTimeServiceImpl  extends CRUDServiceOnlyImpl<AppointmentTime> implements CRUDAppointmentTimeService {





        @Resource
        protected AppointmentTimeMapper appointmentTimeMapper;

        @Override
        protected BaseMapper<AppointmentTime> getMasterMapper() {
                return appointmentTimeMapper;
        }







}


