package com.riverify.school.service;

import java.util.List;
import com.riverify.school.domain.SchoolStudent;

/**
 * 学生信息Service接口
 * 
 * @author riverify
 * @date 2023-03-24
 */
public interface ISchoolStudentService 
{
    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    public SchoolStudent selectSchoolStudentByStudentId(String studentId);

    /**
     * 查询学生信息列表
     * 
     * @param schoolStudent 学生信息
     * @return 学生信息集合
     */
    public List<SchoolStudent> selectSchoolStudentList(SchoolStudent schoolStudent);

    /**
     * 新增学生信息
     * 
     * @param schoolStudent 学生信息
     * @return 结果
     */
    public int insertSchoolStudent(SchoolStudent schoolStudent);

    /**
     * 修改学生信息
     * 
     * @param schoolStudent 学生信息
     * @return 结果
     */
    public int updateSchoolStudent(SchoolStudent schoolStudent);

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deleteSchoolStudentByStudentIds(String[] studentIds);

    /**
     * 删除学生信息信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    public int deleteSchoolStudentByStudentId(String studentId);
}
