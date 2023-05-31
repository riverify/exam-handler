package com.riverify.school.newMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.riverify.school.domain.SchoolManager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 考场安排Mapper接口
 *
 * @author riverify
 * @date 2023-05-23
 */
@Mapper
public interface NSchoolManagerMapper extends BaseMapper<SchoolManager> {

}
