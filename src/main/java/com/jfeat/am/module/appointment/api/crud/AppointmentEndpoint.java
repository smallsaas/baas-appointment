package com.jfeat.am.module.appointment.api.crud;

import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.appointment.services.domain.dao.QueryAppointmentDao;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.common.exception.BusinessCode;
import com.jfeat.am.common.exception.BusinessException;

import java.math.BigDecimal;

import com.jfeat.am.module.appointment.services.domain.service.AppointmentService;
import com.jfeat.am.module.appointment.services.domain.model.AppointmentRecord;
import com.jfeat.am.module.appointment.services.domain.model.AppointmentModel;

import org.springframework.web.bind.annotation.RestController;
import com.jfeat.am.common.controller.BaseController;

import javax.annotation.Resource;
import java.util.Date;


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
    @ApiOperation("新建预约")
    public Tip createAppointment(@RequestBody AppointmentModel entity) {

        Integer affected = 0;
        entity.setMemberId(JWTKit.getUserId(getHttpServletRequest()));
        try {
            affected = appointmentService.createMaster(entity);

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @GetMapping("/{id}")
    @ApiOperation("查看预约详情")
    public Tip getAppointment(@PathVariable Long id) {
        return SuccessTip.create(appointmentService.retrieveMaster(id));
    }

    @BusinessLog(name = "Appointment", value = "update Appointment")
    @PutMapping("/{id}")
    @ApiOperation("修改预约详情")

    public Tip updateAppointment(@PathVariable Long id, @RequestBody AppointmentModel entity) {
        entity.setId(id);
        return SuccessTip.create(appointmentService.updateMaster(entity));
    }

    @BusinessLog(name = "Appointment", value = "delete Appointment")
    @DeleteMapping("/{id}")
    @ApiOperation("删除预约详情")

    public Tip deleteAppointment(@PathVariable Long id) {
        return SuccessTip.create(appointmentService.deleteMaster(id));
    }

    @GetMapping
    @ApiOperation("预约列表")
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
                                 @RequestParam(name = "appointmentTime", required = false) Date appointmentTime,
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
        record.setAppointmentTime(appointmentTime);
        record.setCloseTime(closeTime);
        record.setMemberPhone(memberPhone);
        record.setMemberName(memberName);
        record.setReceptionistId(receptionistId);
        record.setServerId(serverId);
        record.setReceptionistName(receptionistName);
        record.setServerName(serverName);
        record.setFieldC(fieldC);

        page.setRecords(queryAppointmentDao.findAppointmentPage(page, record, orderBy));

        return SuccessTip.create(page);
    }


    @ApiOperation("我的预约列表")
    @GetMapping("/action/appointments")
    public Tip appointments(){
        return SuccessTip.create(appointmentService.myAppointments(JWTKit.getUserId(getHttpServletRequest())));
    }
}
