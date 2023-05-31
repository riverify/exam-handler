package com.riverify.school.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.riverify.school.domain.SchoolClassroom;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考场Mapper接口
 *
 * @author riverify
 * @date 2023-04-19
 */
@Mapper
public interface SchoolClassroomMapper {
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
     * 删除考场
     *
     * @param columnId 考场主键
     * @return 结果
     */
    public int deleteSchoolClassroomByColumnId(Long columnId);

    /**
     * 批量删除考场
     *
     * @param columnIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSchoolClassroomByColumnIds(Long[] columnIds);
}
