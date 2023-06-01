package com.riverify.school.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.riverify.school.mapper.SchoolClassroomMapper;
import com.riverify.school.domain.SchoolClassroom;
import com.riverify.school.service.ISchoolClassroomService;

/**
 * 考场Service业务层处理
 *
 * @author riverify
 * @date 2023-04-19
 */
@Service
public class SchoolClassroomServiceImpl extends ServiceImpl<SchoolClassroomMapper, SchoolClassroom> implements ISchoolClassroomService {
    @Autowired
    private SchoolClassroomMapper schoolClassroomMapper;

    /**
     * 查询考场
     *
     * @param columnId 考场主键
     * @return 考场
     */
    @Override
    public SchoolClassroom selectSchoolClassroomByColumnId(Long columnId) {
        return schoolClassroomMapper.selectSchoolClassroomByColumnId(columnId);
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
     * @param columnIds 需要删除的考场主键
     * @return 结果
     */
    @Override
    public int deleteSchoolClassroomByColumnIds(Long[] columnIds) {
        return schoolClassroomMapper.deleteSchoolClassroomByColumnIds(columnIds);
    }

    /**
     * 删除考场信息
     *
     * @param columnId 考场主键
     * @return 结果
     */
    @Override
    public int deleteSchoolClassroomByColumnId(Long columnId) {
        return schoolClassroomMapper.deleteSchoolClassroomByColumnId(columnId);
    }

    @Override
    public int updateSchoolClassroomStatus(Long columnId, Long status) {
        SchoolClassroom schoolClassroom = new SchoolClassroom();
        schoolClassroom.setColumnId(columnId);
        schoolClassroom.setClassroomStatus(status);
        return updateSchoolClassroom(schoolClassroom);
    }
}
