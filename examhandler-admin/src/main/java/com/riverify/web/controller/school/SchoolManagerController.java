package com.riverify.web.controller.school;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.riverify.school.domain.SchoolClassroom;
import com.riverify.school.domain.SchoolStudent;

import com.riverify.school.service.ISchoolClassroomService;
import com.riverify.school.domain.ManageForm;
import com.riverify.school.service.ISchoolStudentService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.riverify.common.annotation.Log;
import com.riverify.common.core.controller.BaseController;
import com.riverify.common.core.domain.AjaxResult;
import com.riverify.common.enums.BusinessType;
import com.riverify.school.domain.SchoolManager;
import com.riverify.school.service.ISchoolManagerService;
import com.riverify.common.utils.poi.ExcelUtil;
import com.riverify.common.core.page.TableDataInfo;

/**
 * 考场安排Controller
 *
 * @author riverify
 * @date 2023-05-23
 */
@RestController
@EnableTransactionManagement
@RequestMapping("/school/manager")
public class SchoolManagerController extends BaseController {
    @Autowired
    private ISchoolManagerService schoolManagerService;


    @Autowired
    private ISchoolClassroomService schoolClassroomService;

    @Autowired
    private ISchoolStudentService schoolStudentService;

    /**
     * 查询考场安排列表
     */
//    @PreAuthorize("@ss.hasPermi('school:manager:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchoolManager schoolManager) {
        startPage();
        List<SchoolManager> list = schoolManagerService.selectSchoolManagerList(schoolManager);
        return getDataTable(list);
    }

    /**
     * 导出考场安排列表
     */
//    @PreAuthorize("@ss.hasPermi('school:manager:export')")
    @Log(title = "考场安排", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SchoolManager schoolManager) {
        List<SchoolManager> list = schoolManagerService.selectSchoolManagerList(schoolManager);
        ExcelUtil<SchoolManager> util = new ExcelUtil<SchoolManager>(SchoolManager.class);
        util.exportExcel(response, list, "考场安排数据");
    }

    /**
     * 获取考场安排详细信息
     */
//    @PreAuthorize("@ss.hasPermi('school:manager:query')")
    @GetMapping(value = "/{managerId}")
    public AjaxResult getInfo(@PathVariable("managerId") Long managerId) {
        return success(schoolManagerService.selectSchoolManagerByManagerId(managerId));
    }

    /**
     * 新增考场安排
     */
//    @PreAuthorize("@ss.hasPermi('school:manager:add')")
    @Log(title = "考场安排", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchoolManager schoolManager) {
        return toAjax(schoolManagerService.insertSchoolManager(schoolManager));
    }

    /**
     * 修改考场安排
     */
//    @PreAuthorize("@ss.hasPermi('school:manager:edit')")
    @Log(title = "考场安排", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchoolManager schoolManager) {
        return toAjax(schoolManagerService.updateSchoolManager(schoolManager));
    }

    /**
     * 删除考场安排
     */
//    @PreAuthorize("@ss.hasPermi('school:manager:remove')")
    @Log(title = "考场安排", businessType = BusinessType.DELETE)
    @DeleteMapping("/{managerIds}")
    @Transactional
    public AjaxResult remove(@PathVariable Long[] managerIds) {
        // 根据managerIds获取考场安排信息
        for (Long managerId : managerIds) {
            SchoolManager schoolManager = schoolManagerService.selectSchoolManagerByManagerId(managerId);
            // 获取考场id
            Long sid = Long.valueOf(schoolManager.getManagerSid());
            // 删除考场安排，同时使学生表中的考场id置空
            LambdaQueryWrapper<SchoolStudent> studentQueryWrapper = new LambdaQueryWrapper<>();
            studentQueryWrapper.eq(SchoolStudent::getStudentManagerid, sid);
            // 获取学生列表
            List<SchoolStudent> studentList = schoolStudentService.list(studentQueryWrapper);
            // 遍历学生列表
            for (SchoolStudent schoolStudent : studentList) {
                LambdaUpdateWrapper<SchoolStudent> studentUpdateWrapper = new LambdaUpdateWrapper<>();
                studentUpdateWrapper.eq(SchoolStudent::getStudentManagerid, schoolStudent.getStudentManagerid());
                studentUpdateWrapper.set(SchoolStudent::getStudentManagerid, 0L);
                schoolStudentService.update(studentUpdateWrapper);
            }
        }
        return toAjax(schoolManagerService.deleteSchoolManagerByManagerIds(managerIds));
    }

    /**
     * 考场安排
     */
    @Transactional
    @PostMapping("/manage")
    public AjaxResult manage(@RequestBody ManageForm manageForm) {
        // ========== 获取表格前置数据 ===========
        // 获取时间区间
        String[] startend = manageForm.getStartend();
        String start = startend[0];
        String end = startend[1];
        // 获取日期
        Date date = manageForm.getDate();
        Integer relax = manageForm.getRelax();
        // 替换日期中的时间，改成开始时间和结束时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateTime = sdf.format(date) + " " + start;
        String endDateTime = sdf.format(date) + " " + end;

        // 获取考试课程
        String subject = manageForm.getSubject();

        LambdaQueryWrapper<SchoolStudent> studentQueryWrapper = new LambdaQueryWrapper<>();

        LambdaQueryWrapper<SchoolClassroom> classroomQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SchoolManager> managerQueryWrapper = new LambdaQueryWrapper<>();
        // ========== 开始查询 ===========
        // 需要是选择的学科
        studentQueryWrapper.eq(SchoolStudent::getStudentClassname, subject);

        // 需要是未分配考场的学生
        studentQueryWrapper.eq(SchoolStudent::getStudentManagerid, 0);

        // 如果有填写特定教师，那就需要是这个教师的学生
        if (manageForm.getTeacher() != null) {
            studentQueryWrapper.eq(SchoolStudent::getStudentTeacher, manageForm.getTeacher());
        }

//        // 查询这一批的学生人数，如果人数为0，那就不用排了
//        List<SchoolStudent> students = schoolStudentService.list(studentQueryWrapper);
//        if (students.size() == 0) {
//            return AjaxResult.error("没有符合条件的学生");
//        }

        // ========= 开始排考场 =========
        // 寻找空闲考场
        // 先查询所有考场

        List<SchoolClassroom> classrooms = schoolClassroomService.list();
//        List<SchoolClassroom> classrooms = schoolClassroomService.selectSchoolClassroomList(null);
        // 遍历考场，把每个考场带入安排表，寻找非冲突的考场
        for (SchoolClassroom classroom : classrooms) {

            startDateTime = sdf.format(date) + " " + start;

            studentQueryWrapper.eq(SchoolStudent::getStudentCampus, classroom.getClassroomCampus());

            // 查询这一批的学生人数
            List<SchoolStudent> students = schoolStudentService.list(studentQueryWrapper);

            if (students.size() == 0) {
                // 如果人数为0，那就不用排了
                continue;
            }

            if (students.size() > classroom.getClassroomSize()) {
                // 如果人数大于考场容量，那就跳过这个考场
                continue;
            }

            boolean flag = false;

            // 先查询这个考场是否有冲突
            // 获取这个考场的开始结束datetime
            // 先获取考场的安排
            managerQueryWrapper = new LambdaQueryWrapper<>();
            managerQueryWrapper.eq(SchoolManager::getManagerClassroom, classroom.getClassroomNumber());
            List<SchoolManager> schoolManagers = schoolManagerService.list(managerQueryWrapper);
            // 遍历安排，把每个安排带入判断
            loop1:
            for (SchoolManager schoolManager : schoolManagers) {
                Date managerStartdate = schoolManager.getManagerStartdate();
                Date managerEnddate = DateUtils.addMinutes(managerStartdate, manageForm.getDuration());
                managerEnddate = DateUtils.addMinutes(managerEnddate, relax);

                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String managerStartdateString = sdf2.format(managerStartdate);
                String managerEnddateString = sdf2.format(managerEnddate);
                // 如果开始时间在这个考场的开始时间和结束时间之间，那就冲突
                while (startDateTime.compareTo(endDateTime) < 0) {
                    flag = true;

                    // 如果安排中没有和开始结束时间冲突的，那就不冲突，跳出循环，开始排课
                    if (startDateTime.compareTo(managerEnddateString) >= 0) {
                        flag = false;
                        break;
                    }

                    // 如果冲突，那就根据这场考试的relax中间休息时间判断下一时间段是否冲突
                    // 把managerForm的开始时间延后考试和休息的分钟
                    Date date1 = null;
                    try {
                        date1 = sdf2.parse(startDateTime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    date1 = DateUtils.addMinutes(date1, relax);
                    date1 = DateUtils.addMinutes(date1, Math.toIntExact(schoolManager.getManagerDuration()));
                    startDateTime = sdf2.format(date1);
                }
            }

            // 如果冲突，那就跳过这个考场
            if (flag) {
                continue;
            }
            Integer sid = generateValidateCode(9);
            AtomicInteger stdNum = new AtomicInteger();

            // 如果不冲突，那就把这个考场分配给学生，这个考场需要是和学生同校区
            for (SchoolStudent student : students) {
                // 确认学生属于这个考场的校区
                if (student.getStudentCampus().equals(classroom.getClassroomCampus())) {
                    // 分配考场
                    student.setStudentManagerid(Long.valueOf(sid));
                    // 更新学生表
                    schoolStudentService.updateSchoolStudent(student);
                    stdNum.getAndIncrement();
                }
            }

            // 修改安排表
            // 创建安排表对象
            SchoolManager schoolManager = new SchoolManager();
            // 把开始日期变回data类型
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date formateDate = new Date();
            try {
                formateDate = sdf.parse(startDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            schoolManager.setManagerStartdate(formateDate);
            schoolManager.setManagerDuration(Long.valueOf(manageForm.getDuration()));
            schoolManager.setManagerRegion(classroom.getClassroomCampus());
            schoolManager.setManagerClassroom(classroom.getClassroomNumber());
            schoolManager.setManagerSubject(subject);
            schoolManager.setManagerSid(String.valueOf(sid));
            schoolManager.setManagerStudents(Long.valueOf(String.valueOf(stdNum)));

            schoolManagerService.insertSchoolManager(schoolManager);

//            // 修改安排表
//            LambdaUpdateWrapper<SchoolManager> managerUpdateWrapper = new LambdaUpdateWrapper<>();
//            managerUpdateWrapper.set(SchoolManager::getManagerStartdate, startDateTime);
//            managerUpdateWrapper.set(SchoolManager::getManagerDuration, manageForm.getDuration());
//            managerUpdateWrapper.set(SchoolManager::getManagerRegion, classroom.getClassroomCampus());
//            managerUpdateWrapper.set(SchoolManager::getManagerClassroom, classroom.getClassroomNumber());
//            managerUpdateWrapper.set(SchoolManager::getManagerSubject, subject);
//            managerUpdateWrapper.set(SchoolManager::getManagerSid, sid);
//
//            schoolManagerService.update(managerUpdateWrapper);
//
            return toAjax(stdNum.get());

        }

        return AjaxResult.warn("排考失败");

    }

    public static Integer generateValidateCode(int length) {
        Integer code = null;
        if (length == 4) {
            code = new Random().nextInt(9999);//生成随机数，最大为9999
            if (code < 1000) {
                code = code + 1000;//保证随机数为4位数字
            }
        } else if (length == 6) {
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if (code < 100000) {
                code = code + 100000;//保证随机数为6位数字
            }
        } else if (length == 9) {
            code = new Random().nextInt(999999999);//生成随机数，最大为999999
            if (code < 100000000) {
                code = code + 100000000;//保证随机数为9位数字
            }
        } else {
            throw new RuntimeException("只能生成4位或6位或9位数字验证码");
        }
        return code;
    }

}
