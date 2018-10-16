package com.jfeat.am.module.appointment.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.ErrorTip;
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
import com.jfeat.am.module.appointment.services.domain.definition.AppointmentTypeItem;
import com.jfeat.am.module.appointment.services.domain.definition.PaymentMethods;
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

        if (entity.getType() == null) {
            throw new BusinessException(BusinessCode.BadRequest.getCode(), "没有提交类型");
        }

        ///判断类型正确性
        /*String[] types = entity.getType().split("\\+");
        if (types == null || types.length == 0) {
            throw new BusinessException(BusinessCode.BadRequest.getCode(), "没有提交类型");
        }

        for (String type : types) {
            if (type != null && type.length() > 0) {
                if (type.equals(AppointmentTypeItem.SKIN.toString()) ||
                        type.equals(AppointmentTypeItem.DNA.toString()) ||
                        type.equals(AppointmentTypeItem.LIFE_BANK.toString())
                ) {
                    /// OK
                } else {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "类型错误：预约类型 only [SKIN, DNA, LIFE_BANK]");
                }
            }
        }*/

        //判断预约店铺名
        if (entity.getItemId() == null || entity.getItemName() == null) {
            throw new BusinessException(BusinessCode.BadRequest.getCode(), "类型错误：预约店铺不能为空");
        }


        Integer affected = 0;
        entity.setMemberId(JWTKit.getUserId(getHttpServletRequest()));
        try {

            if (entity.getStatus() == null) {
                if (entity.getFee() == null || entity.getFee().compareTo(BigDecimal.ZERO) <= 0) {
                    entity.setStatus(AppointmentStatus.WAIT_TO_STORE.toString());
                } else {
                    entity.setStatus(AppointmentStatus.PAY_PENDING.toString());
                }
            }
            entity.setFieldC(String.valueOf(new Date().getTime() + JWTKit.getUserId(getHttpServletRequest())));
            affected += appointmentService.createMaster(entity);

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
    @ApiOperation(value = "修改预约详情", response = AppointmentModel.class)
    public Tip updateAppointment(@PathVariable Long id, @RequestBody AppointmentModel entity) {
        entity.setId(id);
        Appointment appointment = appointmentService.retrieveMaster(id);
        Long userId = JWTKit.getUserId(getHttpServletRequest());
        if (!appointment.getMemberId().equals(userId) ||
                !ShiroKit.hasPermission(AppointmentPermission.APPOINTMENT_VIEW)) {
            throw new BusinessException(BusinessCode.NoPermission);
        }

        /// 不能直接更改状态，忽略状态更改
        entity.setStatus(appointment.getStatus());
        appointment.setId(id);

        return SuccessTip.create(appointmentService.updateMaster(entity));
    }

    @BusinessLog(name = "Appointment", value = "pay Appointment")
    @PostMapping("/{id}/action/pay")
    @ApiOperation(value = "支付预约", response = PaymentRequest.class)
    public Tip payAppointment(@PathVariable Long id, @RequestBody PaymentRequest payment) {
        if (payment == null || payment.getMethod() == null || payment.getTimestamp() == null) {
            throw new BusinessException(BusinessCode.BadRequest);
        }
        if (payment.getMethod().equals(PaymentMethods.ALIPAY.toString()) ||
                payment.getMethod().equals(PaymentMethods.WECHAT.toString()) ||
                payment.getMethod().equals(PaymentMethods.CASH.toString()) ||
                payment.getMethod().equals(PaymentMethods.WALLET.toString())||
                payment.getMethod().equals(PaymentMethods.CARD.toString())
        ) {
            // ok
        } else {
            return ErrorTip.create(BusinessCode.BadRequest.getCode(), "参数错误 - 支付方法 Only [ALIPAY|WECHAT|CASH|CARD|WALLET]");
        }

        Appointment appointment = appointmentService.retrieveMaster(id);

        /// update status
        if (!appointment.getStatus().equalsIgnoreCase(AppointmentStatus.PAY_PENDING.toString())) {
            return ErrorTip.create(BusinessCode.ErrorStatus);
        }
        appointment.setStatus(AppointmentStatus.WAIT_TO_STORE.toString());

        /// update payment info
        appointment.setPaymentMethod(payment.getMethod());
        appointment.setPaymentTimestamp(payment.getTimestamp());
        appointment.setId(id);
        return SuccessTip.create(appointmentService.updateMaster(appointment));
    }

    @BusinessLog(name = "Appointment", value = "pay Appointment 支付超时")
    @PostMapping("/{id}/action/timeout")
    @ApiOperation(value = "支付超时")
    public Tip timeoutAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);
        if (!appointment.getStatus().equalsIgnoreCase(AppointmentStatus.PAY_PENDING.toString())) {
            return ErrorTip.create(BusinessCode.ErrorStatus);
        }
        appointment.setStatus(AppointmentStatus.PAY_TIMEOUT.toString());
        appointment.setId(id);
        return SuccessTip.create(appointmentService.updateMaster(appointment));
    }


    @BusinessLog(name = "Appointment", value = "Change Appointment status - 取消预约")
    @PostMapping("/{id}/action/cancel")
    @ApiOperation("改变预约状态 - 取消预约(APP)")
    public Tip cancelAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);
        if (appointment.getStatus().equals(AppointmentStatus.PAY_PENDING.toString()) ||
                appointment.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
            appointment.setStatus(AppointmentStatus.CANCELLED.toString());
            appointment.setId(id);
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }

    @BusinessLog(name = "Appointment", value = "Change Appointment status - 会员到店")
    @PostMapping("/{id}/action/check")
    @ApiOperation("改变预约状态 - 会员到店 (iPad)")
    public Tip checkAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);

        if (appointment.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
            appointment.setStatus(AppointmentStatus.ALREADY_TO_STORE.toString());
            appointment.setId(id);
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }


    @BusinessLog(name = "Appointment", value = "Change Appointment status - 会员失约")
    @PostMapping("/{id}/action/miss")
    @ApiOperation("改变预约状态 - 会员失约 (iPad)")
    public Tip changeAppointmentStatus_miss(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);

        if (appointment.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
            appointment.setStatus(AppointmentStatus.MISS_TO_STORE.toString());
            appointment.setId(id);
            return SuccessTip.create(appointmentService.updateMaster(appointment));
        }

        throw new BusinessException(BusinessCode.ErrorStatus);
    }


    @BusinessLog(name = "Appointment", value = "delete Appointment")
    @DeleteMapping("/{id}")
    @ApiOperation("删除预约详情")
    public Tip deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);
        Long userId = JWTKit.getUserId(getHttpServletRequest());
        if (appointment.getMemberId().compareTo(userId) != 0 ||
                !ShiroKit.hasPermission(AppointmentPermission.APPOINTMENT_VIEW)) {
            throw new BusinessException(BusinessCode.NoPermission);
        }
        return SuccessTip.create(appointmentService.deleteMaster(id));
    }

    @BusinessLog(name = "Appointment", value = "delete Appointment")
    @DeleteMapping("/app/{id}")
    @ApiOperation("删除预约详情 无需权限检查")
    public Tip deleteAppointmentWithoutPermission(@PathVariable Long id) {
        Appointment appointment = appointmentService.retrieveMaster(id);
        Long userId = JWTKit.getUserId(getHttpServletRequest());
        if (appointment.getMemberId().compareTo(userId) != 0) {
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
                                 @RequestParam(name = "appointmentTime", required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date[] appointmentTime,
                                 @RequestParam(name = "closeTime", required = false) Date closeTime,
                                 @RequestParam(name = "memberPhone", required = false) String memberPhone,
                                 @RequestParam(name = "memberName", required = false) String memberName,
                                 @RequestParam(name = "receptionistId", required = false) Long receptionistId,
                                 @RequestParam(name = "serverId", required = false) Long serverId,
                                 @RequestParam(name = "receptionistName", required = false) String receptionistName,
                                 @RequestParam(name = "serverName", required = false) String serverName,
                                 @RequestParam(name = "paymentMethod", required = false) String paymentMethod,
                                 @RequestParam(name = "fieldC", required = false) String fieldC,
                                 @RequestParam(name = "search", required = false) String search,
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
        } else {
            /// default order by appointmentTime
            orderBy = "`appointment_time` DESC";
        }

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        AppointmentRecord record = new AppointmentRecord();
        record.setId(id);
        record.setCode(code);
        /*if (type != null && type.length > 0) {
            for (String t : type) {
                if (t.equals(AppointmentTypeItem.SKIN.toString()) ||
                        t.equals(AppointmentTypeItem.DNA.toString()) ||
                        t.equals(AppointmentTypeItem.LIFE_BANK.toString())
                ) {
                    // ok
                } else {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "类型错误：预约类型 only [SKIN, DNA, LIFE_BANK] " + t);
                }
            }
        }*/

        record.setItemId(itemId);
        record.setItemName(itemName);
        record.setItemAddress(itemAddress);
        record.setItemDescription(itemDescription);
        record.setItemIcon(itemIcon);
        record.setMemberId(memberId);
        record.setStatus(status);
        record.setFee(fee);
        record.setCreateTime(createTime);
        //record.setType(type);
        //record.setAppointmentTime(appointmentTime);
        record.setCloseTime(closeTime);
        record.setMemberPhone(memberPhone);
        record.setMemberName(memberName);
        record.setReceptionistId(receptionistId);
        record.setServerId(serverId);
        record.setReceptionistName(receptionistName);
        record.setServerName(serverName);
        record.setFieldC(fieldC);
        record.setPaymentMethod(paymentMethod);

        Date startTime = (appointmentTime != null && appointmentTime.length == 2) ? appointmentTime[0] : null;
        Date endTime = (appointmentTime != null && appointmentTime.length == 2) ? appointmentTime[1] : null;

        List<AppointmentRecord> list = queryAppointmentDao.findAppointmentPage(page, record, orderBy, type, search, startTime, endTime);

        /// 检查待到店的 过期状态，并同时更新状态
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_YEAR, 1);
        today.clear(Calendar.HOUR);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        for (AppointmentRecord r : list) {
            if (r.getStatus().equals(AppointmentStatus.WAIT_TO_STORE.toString())) {
                if (r.getAppointmentTime().compareTo(todayDate) <= 0) {
                    r.setStatus(AppointmentStatus.EXPIRED.toString());
                    appointmentService.updateMaster(r);
                }
            }
        }

        page.setRecords(list);

        return SuccessTip.create(page);
    }
}
