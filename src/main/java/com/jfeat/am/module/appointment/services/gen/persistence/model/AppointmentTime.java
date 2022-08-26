package com.jfeat.am.module.appointment.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code generator
 * @since 2022-08-26
 */
@TableName("t_appointment_time")
@ApiModel(value="AppointmentTime对象", description="")
public class AppointmentTime extends Model<AppointmentTime> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "开始时间")
      private Date startTime;

      @ApiModelProperty(value = "结束时间")
      private Date endTime;

      @ApiModelProperty(value = "是否启动 0-不启用 1-启用")
      private Integer status;

      @ApiModelProperty(value = "周期id")
      private Long periodId;

      @ApiModelProperty(value = "周期名称")
      private String periodName;

      @ApiModelProperty(value = "备注")
      private String note;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public AppointmentTime setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public AppointmentTime setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Date getStartTime() {
        return startTime;
    }

      public AppointmentTime setStartTime(Date startTime) {
          this.startTime = startTime;
          return this;
      }
    
    public Date getEndTime() {
        return endTime;
    }

      public AppointmentTime setEndTime(Date endTime) {
          this.endTime = endTime;
          return this;
      }
    
    public Integer getStatus() {
        return status;
    }

      public AppointmentTime setStatus(Integer status) {
          this.status = status;
          return this;
      }
    
    public Long getPeriodId() {
        return periodId;
    }

      public AppointmentTime setPeriodId(Long periodId) {
          this.periodId = periodId;
          return this;
      }
    
    public String getPeriodName() {
        return periodName;
    }

      public AppointmentTime setPeriodName(String periodName) {
          this.periodName = periodName;
          return this;
      }
    
    public String getNote() {
        return note;
    }

      public AppointmentTime setNote(String note) {
          this.note = note;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public AppointmentTime setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public AppointmentTime setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String START_TIME = "start_time";

      public static final String END_TIME = "end_time";

      public static final String STATUS = "status";

      public static final String PERIOD_ID = "period_id";

      public static final String PERIOD_NAME = "period_name";

      public static final String NOTE = "note";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "AppointmentTime{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", startTime=" + startTime +
                  ", endTime=" + endTime +
                  ", status=" + status +
                  ", periodId=" + periodId +
                  ", periodName=" + periodName +
                  ", note=" + note +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
