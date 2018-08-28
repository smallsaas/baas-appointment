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
public class AppointmentStatusEndpoint extends BaseController {

    @Resource
    AppointmentService appointmentService;

    @BusinessLog(name = "Appointment", value = "Change Appointment status - PAY_TIMEOUT,WAIT_TO_STORE")
    @PostMapping("/appointment/appointments/{id}/transmit")
    @ApiOperation(value = "改变预约状态", response = StatusRequest.class)
    public Tip changeAppointmentStatus_ok(@PathVariable Long id, @RequestBody StatusRequest status) {
        if(status.getStatus()==null){
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Appointment appointment = appointmentService.retrieveMaster(id);

        if(status.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())){
            if(appointment.getStatus().equals(AppointmentStatus.PAY_PENDING.toString())) {
                appointment.setStatus(AppointmentStatus.WAIT_TO_STORE.toString());
            }else{
                throw new BusinessException(BusinessCode.ErrorStatus);
            }

        }else if(status.getStatus().equals(AppointmentStatus.PAY_TIMEOUT.toString())){
            if(appointment.getStatus().equals(AppointmentStatus.PAY_PENDING.toString())) {
                appointment.setStatus(AppointmentStatus.PAY_TIMEOUT.toString());
            }else{
                throw new BusinessException(BusinessCode.ErrorStatus);
            }

        }else{
            //TODO, 其他状态转移
            throw new BusinessException(BusinessCode.NotImplement);
        }

        return SuccessTip.create(appointmentService.updateMaster(appointment));
    }

    @BusinessLog(name = "Appointment", value = "Change Appointment status - 待到店")
    @DeleteMapping("/appointment/appointments/{id}/transmit/paid")
    @ApiOperation("改变预约状态 - APP 待到店")
    public Tip changeAppointmentStatus_ok(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);

        if(appointment.getStatus().equals(AppointmentStatus.PAY_PENDING.toString())) {
            appointment.setStatus(AppointmentStatus.WAIT_TO_STORE.toString());
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }

    @BusinessLog(name = "Appointment", value = "Change Appointment status - 会员到店")
    @DeleteMapping("/appointment/appointments/{id}/transmit/met")
    @ApiOperation("改变预约状态 - iPad 会员到店")
    public Tip changeAppointmentStatus_meet(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);

        if(appointment.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
            appointment.setStatus(AppointmentStatus.ALREADY_TO_STORE.toString());
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }

    @BusinessLog(name = "Appointment", value = "Change Appointment status - 会员失约")
    @DeleteMapping("/appointment/appointments/{id}/transmit/missed")
    @ApiOperation("改变预约状态 - iPad 会员失约")
    public Tip changeAppointmentStatus_miss(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);

        if(appointment.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
            appointment.setStatus(AppointmentStatus.MISS_TO_STORE.toString());
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }

    @BusinessLog(name = "Appointment", value = "Change Appointment status - 取消预约")
    @DeleteMapping("/appointment/appointments/{id}/transmit/cancelled")
    @ApiOperation("改变预约状态 - APP 取消预约")
    public Tip changeAppointmentStatus_cancel(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);

        if(appointment.getStatus().equals(AppointmentStatus.PAY_PENDING.toString()) ||
                appointment.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
            appointment.setStatus(AppointmentStatus.CANCELLED.toString());
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }

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
