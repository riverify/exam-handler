package com.riverify.school.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.riverify.school.mapper.SchoolManagerMapper;
import com.riverify.school.domain.SchoolManager;
import com.riverify.school.service.ISchoolManagerService;

/**
 * 考场安排Service业务层处理
 *
 * @author riverify
 * @date 2023-05-23
 */
@Service
public class SchoolManagerServiceImpl extends ServiceImpl<SchoolManagerMapper, SchoolManager> implements ISchoolManagerService {

    @Autowired
    private SchoolManagerMapper schoolManagerMapper;

    /**
     * 查询考场安排
     *
     * @param managerId 考场安排主键
     * @return 考场安排
     */
    @Override
    public SchoolManager selectSchoolManagerByManagerId(Long managerId) {
        return schoolManagerMapper.selectSchoolManagerByManagerId(managerId);
    }

    /**
     * 查询考场安排列表
     *
     * @param schoolManager 考场安排
     * @return 考场安排
     */
    @Override
    public List<SchoolManager> selectSchoolManagerList(SchoolManager schoolManager) {
        return schoolManagerMapper.selectSchoolManagerList(schoolManager);
    }

    /**
     * 新增考场安排
     *
     * @param schoolManager 考场安排
     * @return 结果
     */
    @Override
    public int insertSchoolManager(SchoolManager schoolManager) {
        return schoolManagerMapper.insertSchoolManager(schoolManager);
    }

    /**
     * 修改考场安排
     *
     * @param schoolManager 考场安排
     * @return 结果
     */
    @Override
    public int updateSchoolManager(SchoolManager schoolManager) {
        return schoolManagerMapper.updateSchoolManager(schoolManager);
    }

    /**
     * 批量删除考场安排
     *
     * @param managerIds 需要删除的考场安排主键
     * @return 结果
     */
    @Override
    public int deleteSchoolManagerByManagerIds(Long[] managerIds) {
        return schoolManagerMapper.deleteSchoolManagerByManagerIds(managerIds);
    }

    /**
     * 删除考场安排信息
     *
     * @param managerId 考场安排主键
     * @return 结果
     */
    @Override
    public int deleteSchoolManagerByManagerId(Long managerId) {
        return schoolManagerMapper.deleteSchoolManagerByManagerId(managerId);
    }
}
