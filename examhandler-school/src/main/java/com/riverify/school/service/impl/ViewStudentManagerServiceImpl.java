package com.riverify.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.riverify.school.mapper.ViewStudentManagerMapper;
import com.riverify.school.domain.ViewStudentManager;
import com.riverify.school.service.IViewStudentManagerService;

/**
 * viewService业务层处理
 *
 * @author riverify
 * @date 2023-06-04
 */
@Service
public class ViewStudentManagerServiceImpl implements IViewStudentManagerService {
    @Autowired
    private ViewStudentManagerMapper viewStudentManagerMapper;

    /**
     * 查询view
     *
     * @param studentId view主键
     * @return view
     */
    @Override
    public ViewStudentManager selectViewStudentManagerByStudentId(String studentId) {
        return viewStudentManagerMapper.selectViewStudentManagerByStudentId(studentId);
    }

    /**
     * 查询view列表
     *
     * @param viewStudentManager view
     * @return view
     */
    @Override
    public List<ViewStudentManager> selectViewStudentManagerList(ViewStudentManager viewStudentManager) {
        return viewStudentManagerMapper.selectViewStudentManagerList(viewStudentManager);
    }

    /**
     * 新增view
     *
     * @param viewStudentManager view
     * @return 结果
     */
    @Override
    public int insertViewStudentManager(ViewStudentManager viewStudentManager) {
        return viewStudentManagerMapper.insertViewStudentManager(viewStudentManager);
    }

    /**
     * 修改view
     *
     * @param viewStudentManager view
     * @return 结果
     */
    @Override
    public int updateViewStudentManager(ViewStudentManager viewStudentManager) {
        return viewStudentManagerMapper.updateViewStudentManager(viewStudentManager);
    }

    /**
     * 批量删除view
     *
     * @param studentIds 需要删除的view主键
     * @return 结果
     */
    @Override
    public int deleteViewStudentManagerByStudentIds(String[] studentIds) {
        return viewStudentManagerMapper.deleteViewStudentManagerByStudentIds(studentIds);
    }

    /**
     * 删除view信息
     *
     * @param studentId view主键
     * @return 结果
     */
    @Override
    public int deleteViewStudentManagerByStudentId(String studentId) {
        return viewStudentManagerMapper.deleteViewStudentManagerByStudentId(studentId);
    }
}
