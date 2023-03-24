package com.riverify.school.service.impl;

import java.util.List;

import com.riverify.common.exception.ServiceException;
import com.riverify.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.riverify.school.mapper.SchoolStudentMapper;
import com.riverify.school.domain.SchoolStudent;
import com.riverify.school.service.ISchoolStudentService;

/**
 * 学生信息Service业务层处理
 * 
 * @author riverify
 * @date 2023-03-24
 */
@Service
public class SchoolStudentServiceImpl implements ISchoolStudentService 
{
    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    @Override
    public SchoolStudent selectSchoolStudentByStudentId(String studentId)
    {
        return schoolStudentMapper.selectSchoolStudentByStudentId(studentId);
    }

    /**
     * 查询学生信息列表
     * 
     * @param schoolStudent 学生信息
     * @return 学生信息
     */
    @Override
    public List<SchoolStudent> selectSchoolStudentList(SchoolStudent schoolStudent)
    {
        return schoolStudentMapper.selectSchoolStudentList(schoolStudent);
    }

    /**
     * 新增学生信息
     * 
     * @param schoolStudent 学生信息
     * @return 结果
     */
    @Override
    public int insertSchoolStudent(SchoolStudent schoolStudent)
    {
        return schoolStudentMapper.insertSchoolStudent(schoolStudent);
    }

    /**
     * 修改学生信息
     * 
     * @param schoolStudent 学生信息
     * @return 结果
     */
    @Override
    public int updateSchoolStudent(SchoolStudent schoolStudent)
    {
        return schoolStudentMapper.updateSchoolStudent(schoolStudent);
    }

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolStudentByStudentIds(String[] studentIds)
    {
        return schoolStudentMapper.deleteSchoolStudentByStudentIds(studentIds);
    }

    /**
     * 删除学生信息信息
     *
     * @param studentId 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolStudentByStudentId(String studentId) {
        return schoolStudentMapper.deleteSchoolStudentByStudentId(studentId);
    }

    /**
     * 导入学生信息
     *
     * @param schoolStudentList 用户数据列表
     * @param updateSupport     是否更新支持，如果已存在，则进行更新数据
     * @param operName          操作用户
     * @return 结果
     */
    @Override
    public String importSchoolStudent(List<SchoolStudent> schoolStudentList, boolean updateSupport, String operName) {
        if (StringUtils.isNull(schoolStudentList) || schoolStudentList.size() == 0) {
            throw new ServiceException("导入学生数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SchoolStudent student : schoolStudentList) {
            try {
                // 默认状态为正常
                if (StringUtils.isNull(student.getStatus())) {
                    student.setStatus("0");
                }
                // 验证是否存在这个学生
                SchoolStudent u = schoolStudentMapper.selectSchoolStudentByStudentId(student.getStudentId());
                if (StringUtils.isNull(u)) {
                    student.setCreateBy(operName);
                    this.insertSchoolStudent(student);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、学生 " + student.getStudentName() + " 导入成功");
                } else if (updateSupport) {
                    student.setUpdateBy(operName);
                    this.updateSchoolStudent(student);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、学生 " + student.getStudentName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、学生 " + student.getStudentName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、学生 " + student.getStudentName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                e.printStackTrace();
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确或存在重复，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
