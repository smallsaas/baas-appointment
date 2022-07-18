package com.jfeat.am.module.appointment.api.crud;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jfeat.am.module.appointment.services.domain.service.AppointmentTypeService;
import com.jfeat.am.module.appointment.services.domain.service.StoreAppointmentTypeService;
import com.jfeat.am.module.appointment.services.persistence.model.AppointmentType;
import com.jfeat.am.module.appointment.services.persistence.model.StoreAppointmentType;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api("店铺与预约条目关联管理")
@RequestMapping("/api/stores")
public class StoreAppointmentTypeEndpoint {

    @Resource
    StoreAppointmentTypeService storeAppointmentTypeService;


    @BusinessLog(name = "StoreAppointmentType", value = "create StoreAppointmentType")
    @PostMapping("/{id}/books/items")
    @ApiOperation(value = "新建关联关系", response = StoreAppointmentType.class)
    public Tip createStoreAppointmentType(@PathVariable Long id , @RequestBody Ids ids) {

        return SuccessTip.create(storeAppointmentTypeService.addRelation(id,ids));

    }


    @BusinessLog(name = "StoreAppointmentType", value = "create StoreAppointmentType")
    @PutMapping("/{id}/books/items")
    @ApiOperation(value = "修改关联关系", response = StoreAppointmentType.class)
    public Tip updateStoreAppointmentType(@PathVariable Long id ,@RequestBody Ids ids) {
        return SuccessTip.create(storeAppointmentTypeService.updateRelation(id,ids));
    }


}
