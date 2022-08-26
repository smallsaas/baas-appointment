package com.jfeat.am.module.appointment.services.domain.dao;

import com.jfeat.am.module.appointment.services.domain.model.AppointmentTimeRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.appointment.services.gen.persistence.model.AppointmentTime;
import com.jfeat.am.module.appointment.services.gen.crud.model.AppointmentTimeModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-26
 */
public interface QueryAppointmentTimeDao extends QueryMasterDao<AppointmentTime> {
   /*
    * Query entity list by page
    */
    List<AppointmentTimeRecord> findAppointmentTimePage(Page<AppointmentTimeRecord> page, @Param("record") AppointmentTimeRecord record,
                                            @Param("tag") String tag,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    AppointmentTimeModel queryMasterModel(@Param("id") Long id);


    /*
     * Query entity model list for slave items
     */
    List<AppointmentTimeModel> queryMasterModelList(@Param("masterId") Object masterId);
}