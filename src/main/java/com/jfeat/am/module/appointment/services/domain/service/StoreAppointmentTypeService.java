package com.jfeat.am.module.appointment.services.domain.service;


import com.jfeat.crud.base.request.Ids;

public interface StoreAppointmentTypeService {



    public Integer addRelation(Long storeId, Ids ids);

    public Integer updateRelation(Long storeId, Ids ids);
}
