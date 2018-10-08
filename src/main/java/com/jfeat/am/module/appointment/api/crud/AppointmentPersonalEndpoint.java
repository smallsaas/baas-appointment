package com.jfeat.am.module.appointment.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.common.exception.BusinessCode;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.shiro.ShiroKit;
import com.jfeat.am.module.appointment.api.permission.AppointmentPermission;
import com.jfeat.am.module.appointment.services.domain.definition.AppointmentStatus;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentService;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
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

    @ApiOperation("我的店铺预约列表 支持两种状态 [WAIT_TO_STORE, DONE]")
    @GetMapping("/appointment/b/appointments")
    public Tip myStoreAppointments(Page<Appointment> page,
                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "itemId", required = true) Long itemId,
                                   @RequestParam(name = "status", required = false) String status,
                                   @RequestParam(name = "isAssigned", required = true, defaultValue = "0") Integer isAssigned,
                                   @RequestParam(name = "doneSituation",required = false)String doneSituation,
                                   @RequestParam(name = "type",required = false)String type
    ) {
        if (status != null && status.length() > 0) {
            if (AppointmentStatus.WAIT_TO_STORE.toString().equals(status) ||
                    "DONE".equals(status)) {
                // ok
            } else {
                throw new BusinessException(BusinessCode.BadRequest.getCode(), "状态仅支持 [WAIT_TO_STORE, DONE]");
            }
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(appointmentService.myBusinessAppointments(page, itemId, status,isAssigned,doneSituation,type));
        return SuccessTip.create(page);
    }

    @ApiOperation("我的预约列表 支持两种状态 [WAIT_TO_STORE, DONE]")
    @GetMapping("/appointment/app/appointments")
    public Tip myAppointments(Page<Appointment> page,
                              @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(name = "status", required = true) String status
    ) {
        if (AppointmentStatus.WAIT_TO_STORE.toString().equals(status) ||
                "DONE".equals(status)) {
            // ok
        } else {
            throw new BusinessException(BusinessCode.BadRequest.getCode(), "状态仅支持 [WAIT_TO_STORE, DONE]");
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long memberId = JWTKit.getUserId(getHttpServletRequest());
        page.setRecords(appointmentService.myAppointments(page, memberId, status));
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
