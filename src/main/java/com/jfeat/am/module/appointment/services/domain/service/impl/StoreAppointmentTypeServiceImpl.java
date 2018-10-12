package com.jfeat.am.module.appointment.services.domain.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfeat.am.common.constant.tips.Ids;
import com.jfeat.am.module.appointment.services.domain.service.StoreAppointmentTypeService;
import com.jfeat.am.module.appointment.services.persistence.dao.StoreAppointmentTypeMapper;
import com.jfeat.am.module.appointment.services.persistence.model.StoreAppointmentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("StoreAppointmentTypeService")
public class StoreAppointmentTypeServiceImpl implements StoreAppointmentTypeService {


    @Resource
    StoreAppointmentTypeMapper storeAppointmentTypeMapper;


    public Integer addRelation(Long storeId, Ids ids){

        Integer affected = 0;

        for (Long id : ids.getIds()){
            StoreAppointmentType relation = new StoreAppointmentType();
            relation.setStoreId(storeId);
            relation.setAppointmentTypeId(id);
            StoreAppointmentType origin = storeAppointmentTypeMapper.selectOne(relation);
            if (origin==null){

                affected += storeAppointmentTypeMapper.insert(relation);
            }else {
                //ok
            }
        }
        return affected;
    }


    public Integer updateRelation(Long storeId, Ids ids){

        Integer affected = 0;

        if (ids.getIds() == null || ids.getIds().size()==0){

            // delete all relation
            affected += storeAppointmentTypeMapper.delete(new EntityWrapper<StoreAppointmentType>().eq(StoreAppointmentType.STORE_ID,storeId));
            return affected;
        }

        affected += storeAppointmentTypeMapper.delete(new EntityWrapper<StoreAppointmentType>().eq(StoreAppointmentType.STORE_ID,storeId));

        for (Long id : ids.getIds()){

            StoreAppointmentType relation = new StoreAppointmentType();
            relation.setStoreId(storeId);
            relation.setAppointmentTypeId(id);
            StoreAppointmentType origin = storeAppointmentTypeMapper.selectOne(relation);
            if (origin==null){

                affected += storeAppointmentTypeMapper.insert(relation);
            }else {
                //ok
            }
        }
        return affected;
    }

}
