package com.riverify.school.service;

import java.util.List;
import com.riverify.school.domain.SchoolStudent;

/**
 * 学生信息Service接口
 *
 * @author riverify
 * @date 2023-05-10
 */
public interface ISchoolStudentService {
    /**
     * 查询学生信息
     *
     * @param sid 学生信息主键
     * @return 学生信息
     */
    public SchoolStudent selectSchoolStudentBySid(Long sid);

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
     * @param sids 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deleteSchoolStudentBySids(Long[] sids);

    /**
     * 删除学生信息信息
     *
     * @param sid 学生信息主键
     * @return 结果
     */
    public int deleteSchoolStudentBySid(Long sid);

    public int updateSchoolStudentStatus(Long sid, String status);

    String importStudent(List<SchoolStudent> studentList, boolean updateSupport, String operName);
}
