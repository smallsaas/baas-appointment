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
            wrapper.eq(Appointment.STATUS, AppointmentStatus.WAIT_TO_STORE.toString()).orderBy(Appointment.CREATE_TIME,false);

        }else if("DONE".equals(status)){
            wrapper.andNew("status={0} OR status={1} OR status={2} OR status={3}",
                    AppointmentStatus.ALREADY_TO_STORE.toString(),
                    AppointmentStatus.MISS_TO_STORE.toString(),
                    AppointmentStatus.CANCELLED.toString(),
                    AppointmentStatus.EXPIRED.toString()
                    ).orderBy(Appointment.CLOSE_TIME,false);
        }

        List<Appointment> appointments = appointmentMapper.selectPage(page, wrapper);
        return appointments;
    }


    /**
     * 店铺预约列表, 预约时间倒序
     * */
    public List<Appointment> myBusinessAppointments(Page<Appointment> page, Long itemId, String status,Integer isAssigned,String doneSituation,String type){
        // check status must be WAIT_TO_STORE, DONE
        // while done
        EntityWrapper<Appointment> wrapper = new EntityWrapper<>();
        wrapper.eq(Appointment.ITEM_ID, itemId);



        if(status!=null) {
            if (AppointmentStatus.WAIT_TO_STORE.toString().equals(status)) {
                if (isAssigned==1){
                wrapper.eq(Appointment.STATUS, AppointmentStatus.WAIT_TO_STORE.toString()).isNotNull(Appointment.RECEPTIONIST_ID);
                }else {
                    wrapper.eq(Appointment.STATUS, AppointmentStatus.WAIT_TO_STORE.toString()).isNull(Appointment.RECEPTIONIST_ID);
                }

            } else if ("DONE".equals(status)) {
                if (doneSituation==null||doneSituation.length()==0){
                    wrapper.andNew("status={0} OR status={1} OR status={2} OR status={3}",
                            AppointmentStatus.ALREADY_TO_STORE.toString(),
                            AppointmentStatus.MISS_TO_STORE.toString(),
                            AppointmentStatus.CANCELLED.toString(),
                            AppointmentStatus.EXPIRED.toString()
                    );

                }else {
                    wrapper.eq(Appointment.STATUS,doneSituation);
                }

            }
        }
        if (type!=null){
            wrapper.like(Appointment.TYPE,type);
        }

        wrapper.orderBy(Appointment.APPOINTMENT_TIME, false);

        List<Appointment> appointments = appointmentMapper.selectPage(page, wrapper);
        return appointments;
    }
}
