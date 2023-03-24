package com.riverify.school.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.riverify.school.mapper.SchoolStudentMapper;
import com.riverify.school.domain.SchoolStudent;
import com.riverify.school.service.ISchoolStudentService;

/**
 * 学生信息Service业务层处理
 * 
 * @author riverify
 * @date 2023-03-24
 */
@Service
public class SchoolStudentServiceImpl implements ISchoolStudentService 
{
    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    @Override
    public SchoolStudent selectSchoolStudentByStudentId(String studentId)
    {
        return schoolStudentMapper.selectSchoolStudentByStudentId(studentId);
    }

    /**
     * 查询学生信息列表
     * 
     * @param schoolStudent 学生信息
     * @return 学生信息
     */
    @Override
    public List<SchoolStudent> selectSchoolStudentList(SchoolStudent schoolStudent)
    {
        return schoolStudentMapper.selectSchoolStudentList(schoolStudent);
    }

    /**
     * 新增学生信息
     * 
     * @param schoolStudent 学生信息
     * @return 结果
     */
    @Override
    public int insertSchoolStudent(SchoolStudent schoolStudent)
    {
        return schoolStudentMapper.insertSchoolStudent(schoolStudent);
    }

    /**
     * 修改学生信息
     * 
     * @param schoolStudent 学生信息
     * @return 结果
     */
    @Override
    public int updateSchoolStudent(SchoolStudent schoolStudent)
    {
        return schoolStudentMapper.updateSchoolStudent(schoolStudent);
    }

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolStudentByStudentIds(String[] studentIds)
    {
        return schoolStudentMapper.deleteSchoolStudentByStudentIds(studentIds);
    }

    /**
     * 删除学生信息信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolStudentByStudentId(String studentId)
    {
        return schoolStudentMapper.deleteSchoolStudentByStudentId(studentId);
    }
}
