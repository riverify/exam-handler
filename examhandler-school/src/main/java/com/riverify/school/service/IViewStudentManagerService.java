package com.riverify.school.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.riverify.school.domain.ViewStudentManager;

/**
 * viewService接口
 *
 * @author riverify
 * @date 2023-06-04
 */
public interface IViewStudentManagerService {
    /**
     * 查询view
     *
     * @param studentId view主键
     * @return view
     */
    public ViewStudentManager selectViewStudentManagerByStudentId(String studentId);

    /**
     * 查询view列表
     *
     * @param viewStudentManager view
     * @return view集合
     */
    public List<ViewStudentManager> selectViewStudentManagerList(ViewStudentManager viewStudentManager);

    /**
     * 新增view
     *
     * @param viewStudentManager view
     * @return 结果
     */
    public int insertViewStudentManager(ViewStudentManager viewStudentManager);

    /**
     * 修改view
     *
     * @param viewStudentManager view
     * @return 结果
     */
    public int updateViewStudentManager(ViewStudentManager viewStudentManager);

    /**
     * 批量删除view
     *
     * @param studentIds 需要删除的view主键集合
     * @return 结果
     */
    public int deleteViewStudentManagerByStudentIds(String[] studentIds);

    /**
     * 删除view信息
     *
     * @param studentId view主键
     * @return 结果
     */
    public int deleteViewStudentManagerByStudentId(String studentId);
}
