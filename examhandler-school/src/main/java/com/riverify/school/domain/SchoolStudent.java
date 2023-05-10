package com.riverify.school.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.riverify.common.annotation.Excel;
import com.riverify.common.core.domain.BaseEntity;

/**
 * 学生信息对象 school_student
 *
 * @author riverify
 * @date 2023-05-10
 */
public class SchoolStudent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long sid;

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

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String studentMajor;

    /**
     * 课程名称
     */
    @Excel(name = "课程名称")
    private String studentClassname;

    /** 教师 */
    @Excel(name = "教师")
    private String studentTeacher;

    /** 学科类别 */
    @Excel(name = "学科类别")
    private String studentCategory;

    /**
     * 校区
     */
    @Excel(name = "校区")
    private String studentCampus;

    /** 星期几 */
    @Excel(name = "星期几")
    private Long studentDay;

    /**
     * 第几节
     */
    @Excel(name = "第几节")
    private Long studentSession;

    /**
     * 上机课地点
     */
    @Excel(name = "上机课地点")
    private String studentClassroom;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getSid() {
        return sid;
    }

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

    public void setStudentCategory(String studentCategory) {
        this.studentCategory = studentCategory;
    }

    public String getStudentCategory() {
        return studentCategory;
    }

    public void setStudentCampus(String studentCampus) {
        this.studentCampus = studentCampus;
    }

    public String getStudentCampus() {
        return studentCampus;
    }

    public void setStudentDay(Long studentDay) {
        this.studentDay = studentDay;
    }

    public Long getStudentDay() {
        return studentDay;
    }

    public void setStudentSession(Long studentSession) {
        this.studentSession = studentSession;
    }

    public Long getStudentSession() {
        return studentSession;
    }

    public void setStudentClassroom(String studentClassroom) {
        this.studentClassroom = studentClassroom;
    }

    public String getStudentClassroom() {
        return studentClassroom;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("sid", getSid())
                .append("studentId", getStudentId())
                .append("studentName", getStudentName())
                .append("studentMajor", getStudentMajor())
                .append("studentClassname", getStudentClassname())
                .append("studentTeacher", getStudentTeacher())
                .append("studentCategory", getStudentCategory())
                .append("studentCampus", getStudentCampus())
                .append("studentDay", getStudentDay())
                .append("studentSession", getStudentSession())
                .append("studentClassroom", getStudentClassroom())
                .append("status", getStatus())
                .toString();
    }
}
