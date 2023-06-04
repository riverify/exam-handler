package com.riverify.school.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.riverify.common.annotation.Excel;
import com.riverify.common.core.domain.BaseEntity;

/**
 * view对象 view_student_manager
 *
 * @author riverify
 * @date 2023-06-04
 */
public class ViewStudentManager extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @Excel(name = "学号")
    private String studentId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String studentName;

    /**
     * 专业名称
     */
    @Excel(name = "专业名称")
    private String studentMajor;

    /**
     * 课程名称
     */
    @Excel(name = "课程名称")
    private String studentClassname;

    /**
     * 教师
     */
    @Excel(name = "教师")
    private String studentTeacher;

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

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentClassname(String studentClassname) {
        this.studentClassname = studentClassname;
    }

    public String getStudentClassname() {
        return studentClassname;
    }

    public void setStudentTeacher(String studentTeacher) {
        this.studentTeacher = studentTeacher;
    }

    public String getStudentTeacher() {
        return studentTeacher;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("studentId", getStudentId())
                .append("studentName", getStudentName())
                .append("studentMajor", getStudentMajor())
                .append("studentClassname", getStudentClassname())
                .append("studentTeacher", getStudentTeacher())
                .append("managerRegion", getManagerRegion())
                .append("managerClassroom", getManagerClassroom())
                .append("managerStartdate", getManagerStartdate())
                .append("managerDuration", getManagerDuration())
                .toString();
    }
}
