package com.jfeat.am.module.appointment.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.common.exception.BusinessCode;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.appointment.services.domain.definition.AppointmentStatus;
import com.jfeat.am.module.appointment.services.domain.definition.StatusRequest;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentService;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
import com.jfeat.am.module.log.annotation.BusinessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * api
 * </p>
 *
 * @author Code Generator
 * @since 2018-06-29
 */
@RestController
@Api("预约管理")
@RequestMapping("/api")
public class AppointmentPersonalEndpoint extends BaseController {

    @Resource
    AppointmentService appointmentService;


    @ApiOperation("我的预约列表")
    @GetMapping("/appointment/app/appointments")
    public Tip appointments(Page<Appointment> page,
                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(name = "status", required = false) String status){
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(appointmentService.myAppointments(page,JWTKit.getUserId(getHttpServletRequest()),status));
        return SuccessTip.create(page);
    }

    @GetMapping("/appointment/app/appointments/{id}")
    @ApiOperation("查看自己的预约详情")
    public Tip getMyAppointment(@PathVariable Long id) {
        throw new BusinessException(BusinessCode.NotSupport.getCode(), "调用 /api/appointment/appointments/{id}");
        /*Appointment appointment = appointmentService.retrieveMaster(id);
        Long userId = JWTKit.getUserId(getHttpServletRequest());
        if (!appointment.getMemberId().equals(userId) ||
                !ShiroKit.hasPermission(AppointmentPermission.APPOINTMENT_VIEW)) {
            throw new BusinessException(BusinessCode.NoPermission);
        }
        return SuccessTip.create(appointment);*/
    }
}
