package com.riverify.school.newService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riverify.school.domain.SchoolClassroom;
import com.riverify.school.newMapper.NSchoolClassroomMapper;
import com.riverify.school.newService.NISchoolClassroomService;
import org.springframework.stereotype.Service;

/**
 * 考场Service业务层处理
 *
 * @author riverify
 * @date 2023-04-19
 */
@Service
public class NSchoolClassroomServiceImpl extends ServiceImpl<NSchoolClassroomMapper, SchoolClassroom> implements NISchoolClassroomService {

}
