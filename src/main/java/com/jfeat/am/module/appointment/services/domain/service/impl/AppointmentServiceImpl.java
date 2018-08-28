package com.jfeat.am.module.appointment.services.domain.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
        List<Appointment> appointments = appointmentMapper.selectPage(page,new EntityWrapper<Appointment>()
                .eq("member_id",memberId)
                .eq("status",status)
                .orderBy(Appointment.APPOINTMENT_TIME, false)
        );
        return appointments;
    }
}
