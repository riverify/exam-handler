package com.riverify.school.newService.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riverify.school.domain.SchoolManager;
import com.riverify.school.newMapper.NSchoolManagerMapper;
import com.riverify.school.newService.NISchoolManagerService;
import org.springframework.stereotype.Service;

/**
 * 考场安排Service业务层处理
 *
 * @author riverify
 * @date 2023-05-23
 */
@Service
public class NSchoolManagerServiceImpl extends ServiceImpl<NSchoolManagerMapper, SchoolManager> implements NISchoolManagerService {


}
