package com.jfeat.am.module.appointment.api.crud;


import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentTypeService;
import com.jfeat.am.module.appointment.services.persistence.model.AppointmentType;
import com.jfeat.am.module.log.annotation.BusinessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api("预约类型管理")
@RequestMapping("/api/appointment/items")
public class AppointmentTypeEndpoint extends BaseController {

    @Resource
    AppointmentTypeService appointmentTypeService;


    @BusinessLog(name = "AppointmentType", value = "create AppointmentType")
    @PostMapping
    @ApiOperation(value = "新建预约类型", response = AppointmentType.class)
    public Tip createAppointment(@RequestBody AppointmentType entity) {

        return SuccessTip.create(appointmentTypeService.createAppointmentType(entity));

    }

    @BusinessLog(name = "AppointmentType", value = "create AppointmentType")
    @GetMapping("/{id}")
    @ApiOperation(value = "查看预约类型", response = AppointmentType.class)
    public Tip showAppointment(@PathVariable Long id) {

        return SuccessTip.create(appointmentTypeService.showAppointmentType(id));

    }


    @BusinessLog(name = "AppointmentType", value = "create AppointmentType")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改预约类型", response = AppointmentType.class)
    public Tip updateAppointment(@PathVariable Long id, @RequestBody AppointmentType entity) {

        return SuccessTip.create(appointmentTypeService.updateAppointmentType(id, entity));

    }


    @BusinessLog(name = "AppointmentType", value = "create AppointmentType")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除预约类型", response = AppointmentType.class)
    public Tip getAppointment(@PathVariable Long id) {

        return SuccessTip.create(appointmentTypeService.deleteAppointmentType(id));

    }


    @BusinessLog(name = "AppointmentType", value = "create AppointmentType")
    @GetMapping
    @ApiOperation(value = "查询预约类型", response = AppointmentType.class)
    public Tip getAppointment(Page<AppointmentType> page,
                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "status", required = false) Integer status

    ) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        page.setRecords(appointmentTypeService.appointmentType(page, name,status));

        return SuccessTip.create(page);

    }


}
