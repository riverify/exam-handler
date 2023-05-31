package com.riverify.school.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @date 2023-05-10
 */
@Service
public class SchoolStudentServiceImpl implements ISchoolStudentService {

    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    /**
     * 查询学生信息
     *
     * @param sid 学生信息主键
     * @return 学生信息
     */
    @Override
    public SchoolStudent selectSchoolStudentBySid(Long sid) {
        return schoolStudentMapper.selectSchoolStudentBySid(sid);
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
     * @param sids 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolStudentBySids(Long[] sids) {
        return schoolStudentMapper.deleteSchoolStudentBySids(sids);
    }

    /**
     * 删除学生信息信息
     *
     * @param sid 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSchoolStudentBySid(Long sid) {
        return schoolStudentMapper.deleteSchoolStudentBySid(sid);
    }

    @Override
    public int updateSchoolStudentStatus(Long sid, String status) {
        SchoolStudent student = new SchoolStudent();
        student.setSid(sid);
        student.setStatus(status);
        // 判断是否已经排课了，如果是的话，就不可以修改状态了
        SchoolStudent one = schoolStudentMapper.selectSchoolStudentBySid(sid);
        if (one != null && one.getStudentManagerid() != 0) {
            throw new ServiceException("该学生已经排课，不可以修改状态！");
        }
        return schoolStudentMapper.updateSchoolStudent(student);
    }

    @Override
    public String importStudent(List<SchoolStudent> studentList, boolean updateSupport, String operName) {
        if (StringUtils.isNull(studentList) || studentList.size() == 0) {
            throw new ServiceException("导入学生数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");


        for (SchoolStudent student : studentList) {
            // 只有数据完全重复，才是失败的，并且传入的studentList中不会存在ids主键，student_id也是可以重复的
            List<SchoolStudent> oneStudentId = schoolStudentMapper.selectSchoolStudentList(student);
            if (oneStudentId.size() > 0) {
                if (updateSupport) {
                    student.setUpdateBy(operName);
                    this.updateSchoolStudent(student);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、学号 " + student.getStudentId() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、学号 " + student.getStudentId() + " 已存在");
                }
            } else {
                student.setCreateBy(operName);
                this.insertSchoolStudent(student);
                successNum++;
                successMsg.append("<br/>" + successNum + "、学号 " + student.getStudentId() + " 导入成功");
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
