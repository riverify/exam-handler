package com.riverify.school.newService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riverify.school.domain.SchoolStudent;
import com.riverify.school.newMapper.NSchoolStudentMapper;
import com.riverify.school.newService.NISchoolStudentService;
import org.springframework.stereotype.Service;

/**
 * 学生信息Service业务层处理
 *
 * @author riverify
 * @date 2023-05-10
 */
@Service
public class NSchoolStudentServiceImpl extends ServiceImpl<NSchoolStudentMapper, SchoolStudent> implements NISchoolStudentService {


}
