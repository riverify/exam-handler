package com.riverify.school.mapper;

import java.util.List;
import com.riverify.school.domain.SchoolStudent;

/**
 * 学生信息Mapper接口
 * 
 * @author riverify
 * @date 2023-03-24
 */
public interface SchoolStudentMapper 
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
     * 删除学生信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    public int deleteSchoolStudentByStudentId(String studentId);

    /**
     * 批量删除学生信息
     *
     * @param studentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSchoolStudentByStudentIds(String[] studentIds);

    public int countSchoolStudent();
}
