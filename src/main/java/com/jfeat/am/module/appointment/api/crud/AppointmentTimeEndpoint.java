
package com.jfeat.am.module.appointment.api.crud;


import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.appointment.services.domain.dao.QueryAppointmentTimeDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.appointment.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.appointment.services.domain.service.*;
import com.jfeat.am.module.appointment.services.domain.model.AppointmentTimeRecord;
import com.jfeat.am.module.appointment.services.gen.persistence.model.AppointmentTime;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-08-26
 */
@RestController
@Api("AppointmentTime")
@RequestMapping("/api/booking/book/appointmentTimes")
public class AppointmentTimeEndpoint {

    @Resource
    AppointmentTimeService appointmentTimeService;

    @Resource
    QueryAppointmentTimeDao queryAppointmentTimeDao;


    @BusinessLog(name = "AppointmentTime", value = "create AppointmentTime")
    @Permission(AppointmentTimePermission.APPOINTMENTTIME_NEW)
    @PostMapping
    @ApiOperation(value = "新建 AppointmentTime", response = AppointmentTime.class)
    public Tip createAppointmentTime(@RequestBody AppointmentTime entity) {
        Integer affected = 0;
        try {
            affected = appointmentTimeService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(AppointmentTimePermission.APPOINTMENTTIME_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 AppointmentTime", response = AppointmentTime.class)
    public Tip getAppointmentTime(@PathVariable Long id) {
        return SuccessTip.create(appointmentTimeService.queryMasterModel(queryAppointmentTimeDao, id));
    }

    @BusinessLog(name = "AppointmentTime", value = "update AppointmentTime")
    @Permission(AppointmentTimePermission.APPOINTMENTTIME_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 AppointmentTime", response = AppointmentTime.class)
    public Tip updateAppointmentTime(@PathVariable Long id, @RequestBody AppointmentTime entity) {
        entity.setId(id);
        return SuccessTip.create(appointmentTimeService.updateMaster(entity));
    }

    @BusinessLog(name = "AppointmentTime", value = "delete AppointmentTime")
    @Permission(AppointmentTimePermission.APPOINTMENTTIME_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 AppointmentTime")
    public Tip deleteAppointmentTime(@PathVariable Long id) {
        return SuccessTip.create(appointmentTimeService.deleteMaster(id));
    }

    @Permission(AppointmentTimePermission.APPOINTMENTTIME_VIEW)
    @ApiOperation(value = "AppointmentTime 列表信息", response = AppointmentTimeRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "startTime", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", dataType = "Date"),
            @ApiImplicitParam(name = "status", dataType = "Integer"),
            @ApiImplicitParam(name = "periodId", dataType = "Long"),
            @ApiImplicitParam(name = "periodName", dataType = "String"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryAppointmentTimePage(Page<AppointmentTimeRecord> page,
                                        @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                        // for tag feature query
                                        @RequestParam(name = "tag", required = false) String tag,
                                        // end tag
                                        @RequestParam(name = "search", required = false) String search,

                                        @RequestParam(name = "userId", required = false) Long userId,

                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(name = "startTime", required = false) Date startTime,

                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(name = "endTime", required = false) Date endTime,

                                        @RequestParam(name = "status", required = false) Integer status,

                                        @RequestParam(name = "periodId", required = false) Long periodId,

                                        @RequestParam(name = "periodName", required = false) String periodName,

                                        @RequestParam(name = "note", required = false) String note,

                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(name = "createTime", required = false) Date createTime,

                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(name = "updateTime", required = false) Date updateTime,
                                        @RequestParam(name = "orderBy", required = false) String orderBy,
                                        @RequestParam(name = "sort", required = false) String sort) {

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String sortPattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(sortPattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        AppointmentTimeRecord record = new AppointmentTimeRecord();
        record.setUserId(userId);
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        record.setStatus(status);
        record.setPeriodId(periodId);
        record.setPeriodName(periodName);
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);
        List<AppointmentTimeRecord> appointmentTimePage = queryAppointmentTimeDao.findAppointmentTimePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(appointmentTimePage);

        return SuccessTip.create(page);
    }
}

