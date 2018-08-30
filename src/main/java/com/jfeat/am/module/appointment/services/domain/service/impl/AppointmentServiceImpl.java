package com.jfeat.am.module.appointment.services.domain.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.appointment.services.domain.definition.AppointmentStatus;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentService;

import com.jfeat.am.module.appointment.services.crud.service.impl.CRUDAppointmentServiceImpl;
import com.jfeat.am.module.appointment.services.persistence.dao.AppointmentMapper;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("AppointmentService")
public class AppointmentServiceImpl extends CRUDAppointmentServiceImpl implements AppointmentService {

    @Resource
    AppointmentMapper appointmentMapper;

    /**
     * 我的预约列表, 预约时间倒序
     * */
    public List<Appointment> myAppointments(Page<Appointment> page, Long memberId, String status){
        // check status must be WAIT_TO_STORE, DONE
        EntityWrapper<Appointment> wrapper = new EntityWrapper<>();
        wrapper.eq(Appointment.MEMBER_ID, memberId);

        if(AppointmentStatus.WAIT_TO_STORE.toString().equals(status)) {
            wrapper.eq(Appointment.STATUS, AppointmentStatus.WAIT_TO_STORE.toString());

        }else if("DONE".equals(status)){
            wrapper.andNew("status={0} OR status={1} OR status={2} OR status={3}",
                    AppointmentStatus.ALREADY_TO_STORE.toString(),
                    AppointmentStatus.MISS_TO_STORE.toString(),
                    AppointmentStatus.CANCELLED.toString(),
                    AppointmentStatus.EXPIRED.toString()
                    );
        }

        wrapper.orderBy(Appointment.APPOINTMENT_TIME, false);

        List<Appointment> appointments = appointmentMapper.selectPage(page, wrapper);
        return appointments;
    }
}
