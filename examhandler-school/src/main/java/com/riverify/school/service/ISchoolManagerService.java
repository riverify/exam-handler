package com.riverify.school.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.riverify.school.domain.SchoolManager;

/**
 * 考场安排Service接口
 *
 * @author riverify
 * @date 2023-05-23
 */
public interface ISchoolManagerService extends IService<SchoolManager> {
    /**
     * 查询考场安排
     *
     * @param managerId 考场安排主键
     * @return 考场安排
     */
    public SchoolManager selectSchoolManagerByManagerId(Long managerId);

    /**
     * 查询考场安排列表
     *
     * @param schoolManager 考场安排
     * @return 考场安排集合
     */
    public List<SchoolManager> selectSchoolManagerList(SchoolManager schoolManager);

    /**
     * 新增考场安排
     *
     * @param schoolManager 考场安排
     * @return 结果
     */
    public int insertSchoolManager(SchoolManager schoolManager);

    /**
     * 修改考场安排
     *
     * @param schoolManager 考场安排
     * @return 结果
     */
    public int updateSchoolManager(SchoolManager schoolManager);

    /**
     * 批量删除考场安排
     *
     * @param managerIds 需要删除的考场安排主键集合
     * @return 结果
     */
    public int deleteSchoolManagerByManagerIds(Long[] managerIds);

    /**
     * 删除考场安排信息
     *
     * @param managerId 考场安排主键
     * @return 结果
     */
    public int deleteSchoolManagerByManagerId(Long managerId);
}
