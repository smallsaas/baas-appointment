package com.jfeat.am.module.appointment.services.domain.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.appointment.services.domain.dao.QueryAppointmentTypeDao;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentTypeService;
import com.jfeat.am.module.appointment.services.persistence.dao.AppointmentTypeMapper;
import com.jfeat.am.module.appointment.services.persistence.model.AppointmentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("AppointmentTypeService")
public class AppointmentTypeServiceImpl implements AppointmentTypeService {

    @Resource
    AppointmentTypeMapper appointmentTypeMapper;

    @Resource
    QueryAppointmentTypeDao queryAppointmentTypeDao;


    public Integer createAppointmentType(AppointmentType entity){


        return appointmentTypeMapper.insert(entity);
    }


    public AppointmentType showAppointmentType(Long id){


        return appointmentTypeMapper.selectById(id);
    }


    public Integer updateAppointmentType(Long id,AppointmentType entity){
        entity.setId(id);
        return appointmentTypeMapper.updateById(entity);
    }


    public Integer deleteAppointmentType(Long id){
        return appointmentTypeMapper.deleteById(id);

    }

    public List<AppointmentType> appointmentType(Page<AppointmentType> page,String name,String status){
        if (name!=null&&name.length()>0){

            List<AppointmentType> types = appointmentTypeMapper.selectPage(page,
                    new EntityWrapper<AppointmentType>().eq("type",name).eq("status",status));
            return types;
        }else {
            List<AppointmentType> types = appointmentTypeMapper.selectPage(page,
                    new EntityWrapper<AppointmentType>().eq("status",status));
            return types;

        }

    }

    public List<AppointmentType> findAppointmentType(Long storeId){

        return queryAppointmentTypeDao.findAppointmentType(storeId);
    }

}
