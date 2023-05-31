package com.riverify.school.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.riverify.common.annotation.Excel;
import com.riverify.common.core.domain.BaseEntity;

/**
 * 考场安排对象 school_manager
 *
 * @author riverify
 * @date 2023-05-24
 */
public class SchoolManager extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 考场编号
     */
    private Long managerId;

    /**
     * 准考证号
     */
    @Excel(name = "准考证号")
    private String managerSid;

    /**
     * 考场校区
     */
    @Excel(name = "考场校区")
    private String managerRegion;

    /**
     * 考场教室
     */
    @Excel(name = "考场教室")
    private String managerClassroom;

    /**
     * 考试人数
     */
    @Excel(name = "考试人数")
    private Long managerStudents;

    /**
     * 开考时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开考时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date managerStartdate;

    /**
     * 考试时长
     */
    @Excel(name = "考试时长")
    private Long managerDuration;

    /**
     * 考试课程
     */
    @Excel(name = "考试课程")
    private String managerSubject;

    /**
     * 学科类型
     */
    @Excel(name = "学科类型")
    private String managerType;

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerSid(String managerSid) {
        this.managerSid = managerSid;
    }

    public String getManagerSid() {
        return managerSid;
    }

    public void setManagerRegion(String managerRegion) {
        this.managerRegion = managerRegion;
    }

    public String getManagerRegion() {
        return managerRegion;
    }

    public void setManagerClassroom(String managerClassroom) {
        this.managerClassroom = managerClassroom;
    }

    public String getManagerClassroom() {
        return managerClassroom;
    }

    public void setManagerStudents(Long managerStudents) {
        this.managerStudents = managerStudents;
    }

    public Long getManagerStudents() {
        return managerStudents;
    }

    public void setManagerStartdate(Date managerStartdate) {
        this.managerStartdate = managerStartdate;
    }

    public Date getManagerStartdate() {
        return managerStartdate;
    }

    public void setManagerDuration(Long managerDuration) {
        this.managerDuration = managerDuration;
    }

    public Long getManagerDuration() {
        return managerDuration;
    }

    public void setManagerSubject(String managerSubject) {
        this.managerSubject = managerSubject;
    }

    public String getManagerSubject() {
        return managerSubject;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public String getManagerType() {
        return managerType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("managerId", getManagerId())
                .append("managerSid", getManagerSid())
                .append("managerRegion", getManagerRegion())
                .append("managerClassroom", getManagerClassroom())
                .append("managerStudents", getManagerStudents())
                .append("managerStartdate", getManagerStartdate())
                .append("managerDuration", getManagerDuration())
                .append("managerSubject", getManagerSubject())
                .append("managerType", getManagerType())
                .toString();
    }
}
