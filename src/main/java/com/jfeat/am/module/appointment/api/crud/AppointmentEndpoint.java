package com.jfeat.am.module.appointment.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.common.exception.BusinessCode;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.shiro.ShiroKit;
import com.jfeat.am.module.appointment.api.permission.AppointmentPermission;
import com.jfeat.am.module.appointment.services.domain.dao.QueryAppointmentDao;
import com.jfeat.am.module.appointment.services.domain.definition.AppointmentStatus;
import com.jfeat.am.module.appointment.services.domain.model.AppointmentModel;
import com.jfeat.am.module.appointment.services.domain.model.AppointmentRecord;
import com.jfeat.am.module.appointment.services.domain.service.AppointmentService;
import com.jfeat.am.module.appointment.services.persistence.model.Appointment;
import com.jfeat.am.module.log.annotation.BusinessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
@RequestMapping("/api/appointment/appointments")
public class AppointmentEndpoint extends BaseController {


    @Resource
    AppointmentService appointmentService;

    @Resource
    QueryAppointmentDao queryAppointmentDao;

    @BusinessLog(name = "Appointment", value = "create Appointment")
    @PostMapping
    @ApiOperation(value = "新建预约", response = AppointmentModel.class)
    public Tip createAppointment(@RequestBody AppointmentModel entity) {

        Integer affected = 0;
        entity.setMemberId(JWTKit.getUserId(getHttpServletRequest()));
        try {

            if(entity.getStatus()==null) {
                if (entity.getFee()==null || entity.getFee().compareTo(BigDecimal.ZERO)<=0 ) {
                    entity.setStatus(AppointmentStatus.WAIT_TO_STORE.toString());
                }else{
                    entity.setStatus(AppointmentStatus.PAY_PENDING.toString());
                }
            }

            affected += appointmentService.createMaster(entity);

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(entity.getCode());
    }

    @GetMapping("/{id}")
    @ApiOperation("查看预约详情")
    public Tip getAppointment(@PathVariable Long id) {
        return SuccessTip.create(appointmentService.retrieveMaster(id));
    }

    @BusinessLog(name = "Appointment", value = "update Appointment")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改预约详情", response = AppointmentModel.class)
    public Tip updateAppointment(@PathVariable Long id, @RequestBody AppointmentModel entity) {
        entity.setId(id);
        Appointment appointment = appointmentService.retrieveMaster(id);
        Long userId = JWTKit.getUserId(getHttpServletRequest());
        if (!appointment.getMemberId().equals(userId) ||
                !ShiroKit.hasPermission(AppointmentPermission.APPOINTMENT_VIEW)){
            throw new BusinessException(BusinessCode.NoPermission);
        }

        /// 不能直接更改状态，忽略状态更改
        entity.setStatus(appointment.getStatus());

        return SuccessTip.create(appointmentService.updateMaster(entity));
    }

    @BusinessLog(name = "Appointment", value = "delete Appointment")
    @DeleteMapping("/{id}")
    @ApiOperation("删除预约详情")
    public Tip deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);
        Long userId = JWTKit.getUserId(getHttpServletRequest());
        if (!appointment.getMemberId().equals(userId) ||
                !ShiroKit.hasPermission(AppointmentPermission.APPOINTMENT_VIEW)){
            throw new BusinessException(BusinessCode.NoPermission);
        }
        return SuccessTip.create(appointmentService.deleteMaster(id));
    }

    @GetMapping
    @ApiOperation("查询预约列表")
    public Tip queryAppointments(Page<AppointmentRecord> page,
                                 @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam(name = "id", required = false) Long id,
                                 @RequestParam(name = "code", required = false) String code,
                                 @RequestParam(name = "type", required = false) String type,
                                 @RequestParam(name = "itemId", required = false) Long itemId,
                                 @RequestParam(name = "itemName", required = false) String itemName,
                                 @RequestParam(name = "itemAddress", required = false) String itemAddress,
                                 @RequestParam(name = "itemDescription", required = false) String itemDescription,
                                 @RequestParam(name = "itemIcon", required = false) String itemIcon,
                                 @RequestParam(name = "memberId", required = false) Long memberId,
                                 @RequestParam(name = "status", required = false) String status,
                                 @RequestParam(name = "fee", required = false) BigDecimal fee,
                                 @RequestParam(name = "createTime", required = false) Date createTime,
                                 @RequestParam(name = "appointmentTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date[] appointmentTime,
                                 @RequestParam(name = "closeTime", required = false) Date closeTime,
                                 @RequestParam(name = "memberPhone", required = false) String memberPhone,
                                 @RequestParam(name = "memberName", required = false) String memberName,
                                 @RequestParam(name = "receptionistId", required = false) Long receptionistId,
                                 @RequestParam(name = "serverId", required = false) Long serverId,
                                 @RequestParam(name = "receptionistName", required = false) String receptionistName,
                                 @RequestParam(name = "serverName", required = false) String serverName,
                                 @RequestParam(name = "fieldC", required = false) String fieldC,
                                 @RequestParam(name = "orderBy", required = false) String orderBy,
                                 @RequestParam(name = "sort", required = false) String sort) {

        /// 时间倒序
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }else{
            /// default order by appointmentTime
            orderBy = "`appointment_time` DESC";
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        AppointmentRecord record = new AppointmentRecord();
        record.setId(id);
        record.setCode(code);
        record.setType(type);
        record.setItemId(itemId);
        record.setItemName(itemName);
        record.setItemAddress(itemAddress);
        record.setItemDescription(itemDescription);
        record.setItemIcon(itemIcon);
        record.setMemberId(memberId);
        record.setStatus(status);
        record.setFee(fee);
        record.setCreateTime(createTime);
        //record.setAppointmentTime(appointmentTime);
        record.setCloseTime(closeTime);
        record.setMemberPhone(memberPhone);
        record.setMemberName(memberName);
        record.setReceptionistId(receptionistId);
        record.setServerId(serverId);
        record.setReceptionistName(receptionistName);
        record.setServerName(serverName);
        record.setFieldC(fieldC);

        Date startTime = (appointmentTime!=null && appointmentTime.length == 2)? appointmentTime[0] : null;
        Date endTime = (appointmentTime!=null && appointmentTime.length == 2)? appointmentTime[1] : null;

        List<AppointmentRecord> list = queryAppointmentDao.findAppointmentPage(page, record, orderBy, startTime, endTime);

        /// 检查待到店的 过期状态，并同时更新状态
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_YEAR, 1);
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        for (AppointmentRecord r : list){
            if(r.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
                if (r.getAppointmentTime().compareTo(todayDate) >= 0) {
                    r.setStatus(AppointmentStatus.EXPIRED.toString());
                    appointmentService.updateMaster(r);
                }
            }
        }

        page.setRecords(list);

        return SuccessTip.create(page);
    }
}
