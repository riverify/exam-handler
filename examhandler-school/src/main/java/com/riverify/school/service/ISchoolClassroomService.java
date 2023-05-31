package com.riverify.school.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.riverify.school.domain.SchoolClassroom;
import org.springframework.stereotype.Service;

/**
 * 考场Service接口
 *
 * @author riverify
 * @date 2023-04-19
 */
public interface ISchoolClassroomService {
    /**
     * 查询考场
     *
     * @param columnId 考场主键
     * @return 考场
     */
    public SchoolClassroom selectSchoolClassroomByColumnId(Long columnId);

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
     * @param columnIds 需要删除的考场主键集合
     * @return 结果
     */
    public int deleteSchoolClassroomByColumnIds(Long[] columnIds);

    /**
     * 删除考场信息
     *
     * @param columnId 考场主键
     * @return 结果
     */
    public int deleteSchoolClassroomByColumnId(Long columnId);

    /**
     * 更新考场状态
     *
     * @param columnId 考场ID
     * @param status   状态
     * @return 结果
     */
    int updateSchoolClassroomStatus(Long columnId, Long status);
}
