package com.riverify.school.newMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.riverify.school.domain.SchoolClassroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 考场Mapper接口
 *
 * @author riverify
 * @date 2023-04-19
 */
@Mapper
public interface NSchoolClassroomMapper extends BaseMapper<SchoolClassroom> {


}
