package com.jfeat.am.module.appointment.services.domain.service.impl;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentTimeService;
import com.jfeat.am.module.appointment.services.gen.crud.service.impl.CRUDAppointmentTimeServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("appointmentTimeService")
public class AppointmentTimeServiceImpl extends CRUDAppointmentTimeServiceImpl implements AppointmentTimeService {

    @Override
    protected String entityName() {
        return "AppointmentTime";
    }


                            }
