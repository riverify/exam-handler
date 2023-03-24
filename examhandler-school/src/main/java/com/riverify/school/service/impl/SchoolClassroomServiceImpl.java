package com.riverify.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.riverify.school.mapper.SchoolClassroomMapper;
import com.riverify.school.domain.SchoolClassroom;
import com.riverify.school.service.ISchoolClassroomService;

/**
 * 考场Service业务层处理
 *
 * @author riverify
 * @date 2023-03-24
 */
@Service
public class SchoolClassroomServiceImpl implements ISchoolClassroomService {
    @Autowired
    private SchoolClassroomMapper schoolClassroomMapper;

    /**
     * 查询考场
     *
     * @param classroomNumber 考场主键
     * @return 考场
     */
    @Override
    public SchoolClassroom selectSchoolClassroomByClassroomNumber(String classroomNumber) {
        return schoolClassroomMapper.selectSchoolClassroomByClassroomNumber(classroomNumber);
    }

    /**
     * 查询考场列表
     *
     * @param schoolClassroom 考场
     * @return 考场
     */
    @Override
    public List<SchoolClassroom> selectSchoolClassroomList(SchoolClassroom schoolClassroom) {
        return schoolClassroomMapper.selectSchoolClassroomList(schoolClassroom);
    }

    /**
     * 新增考场
     *
     * @param schoolClassroom 考场
     * @return 结果
     */
    @Override
    public int insertSchoolClassroom(SchoolClassroom schoolClassroom) {
        return schoolClassroomMapper.insertSchoolClassroom(schoolClassroom);
    }

    /**
     * 修改考场
     *
     * @param schoolClassroom 考场
     * @return 结果
     */
    @Override
    public int updateSchoolClassroom(SchoolClassroom schoolClassroom) {
        return schoolClassroomMapper.updateSchoolClassroom(schoolClassroom);
    }

    /**
     * 批量删除考场
     *
     * @param classroomNumbers 需要删除的考场主键
     * @return 结果
     */
    @Override
    public int deleteSchoolClassroomByClassroomNumbers(String[] classroomNumbers) {
        return schoolClassroomMapper.deleteSchoolClassroomByClassroomNumbers(classroomNumbers);
    }

    /**
     * 删除考场信息
     *
     * @param classroomNumber 考场主键
     * @return 结果
     */
    @Override
    public int deleteSchoolClassroomByClassroomNumber(String classroomNumber) {
        return schoolClassroomMapper.deleteSchoolClassroomByClassroomNumber(classroomNumber);
    }
}
