package com.riverify.school.service;

import java.util.List;

import com.riverify.school.domain.SchoolClassroom;

/**
 * 考场Service接口
 *
 * @author riverify
 * @date 2023-03-24
 */
public interface ISchoolClassroomService {
    /**
     * 查询考场
     *
     * @param classroomNumber 考场主键
     * @return 考场
     */
    public SchoolClassroom selectSchoolClassroomByClassroomNumber(String classroomNumber);

    /**
     * 查询考场列表
     *
     * @param schoolClassroom 考场
     * @return 考场集合
     */
    public List<SchoolClassroom> selectSchoolClassroomList(SchoolClassroom schoolClassroom);

    /**
     * 新增考场
     *
     * @param schoolClassroom 考场
     * @return 结果
     */
    public int insertSchoolClassroom(SchoolClassroom schoolClassroom);

    /**
     * 修改考场
     *
     * @param schoolClassroom 考场
     * @return 结果
     */
    public int updateSchoolClassroom(SchoolClassroom schoolClassroom);

    /**
     * 批量删除考场
     *
     * @param classroomNumbers 需要删除的考场主键集合
     * @return 结果
     */
    public int deleteSchoolClassroomByClassroomNumbers(String[] classroomNumbers);

    /**
     * 删除考场信息
     *
     * @param classroomNumber 考场主键
     * @return 结果
     */
    public int deleteSchoolClassroomByClassroomNumber(String classroomNumber);
}
