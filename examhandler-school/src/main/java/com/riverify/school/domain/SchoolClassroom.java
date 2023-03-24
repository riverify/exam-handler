package com.riverify.school.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.riverify.common.annotation.Excel;
import com.riverify.common.core.domain.BaseEntity;

/**
 * 考场对象 school_classroom
 *
 * @author riverify
 * @date 2023-03-24
 */
public class SchoolClassroom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 考场号
     */
    @Excel(name = "考场号")
    private String classroomNumber;

    /**
     * 所在校区
     */
    @Excel(name = "所在校区")
    private String classroomCampus;

    /**
     * 可用座位
     */
    @Excel(name = "可用座位")
    private Long classroomSize;

    /**
     * 考场状态（0：空闲）
     */
    @Excel(name = "考场状态", readConverterExp = "0=：空闲")
    private Long classroomStatus;

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomCampus(String classroomCampus) {
        this.classroomCampus = classroomCampus;
    }

    public String getClassroomCampus() {
        return classroomCampus;
    }

    public void setClassroomSize(Long classroomSize) {
        this.classroomSize = classroomSize;
    }

    public Long getClassroomSize() {
        return classroomSize;
    }

    public void setClassroomStatus(Long classroomStatus) {
        this.classroomStatus = classroomStatus;
    }

    public Long getClassroomStatus() {
        return classroomStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("classroomNumber", getClassroomNumber())
                .append("classroomCampus", getClassroomCampus())
                .append("classroomSize", getClassroomSize())
                .append("classroomStatus", getClassroomStatus())
                .toString();
    }
}
