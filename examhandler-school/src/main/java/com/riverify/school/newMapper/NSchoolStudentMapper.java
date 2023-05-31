package com.riverify.school.newMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.riverify.school.domain.SchoolStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生信息Mapper接口
 *
 * @author riverify
 * @date 2023-05-10
 */
@Mapper
public interface NSchoolStudentMapper extends BaseMapper<SchoolStudent> {

}
