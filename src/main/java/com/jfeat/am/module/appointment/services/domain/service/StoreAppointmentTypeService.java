package com.jfeat.am.module.appointment.services.domain.service;

import com.jfeat.am.common.constant.tips.Ids;

public interface StoreAppointmentTypeService {



    public Integer addRelation(Long storeId, Ids ids);

    public Integer updateRelation(Long storeId, Ids ids);
}
