package com.riverify.web.controller.school;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.riverify.common.utils.DateUtils;
import com.riverify.school.domain.SchoolClassroom;
import com.riverify.school.domain.SchoolStudent;
import com.riverify.school.newMapper.NSchoolClassroomMapper;
import com.riverify.school.newService.NISchoolClassroomService;
import com.riverify.school.newService.NISchoolManagerService;
import com.riverify.school.newService.NISchoolStudentService;
import com.riverify.school.service.ISchoolClassroomService;
import com.riverify.school.domain.ManageForm;
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
    private NISchoolManagerService nschoolManagerService;

    @Autowired
    private NISchoolStudentService nschoolStudentService;

    @Autowired
    private NISchoolClassroomService nschoolClassroomService;

    @Autowired
    private NSchoolClassroomMapper nSchoolClassroomMapper;


    @Autowired
    private ISchoolClassroomService schoolClassroomService;

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
    public AjaxResult remove(@PathVariable Long[] managerIds) {
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
        // 合并日期和时间 (年月日)
        String startDateTime = date.toString() + " " + start;
        String endDateTime = date.toString() + " " + end;

        // 获取考试课程
        String subject = manageForm.getSubject();

        LambdaQueryWrapper<SchoolStudent> studentQueryWrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<SchoolStudent> studentUpdateWrapper = new LambdaUpdateWrapper<>();
        LambdaQueryWrapper<SchoolClassroom> classroomQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SchoolManager> managerQueryWrapper = new LambdaQueryWrapper<>();
        // ========== 开始查询 ===========
        // 需要是选择的学科
        studentUpdateWrapper.eq(SchoolStudent::getStudentClassname, subject);
        studentQueryWrapper.eq(SchoolStudent::getStudentClassname, subject);
        // 需要是未分配考场的学生
        studentUpdateWrapper.eq(SchoolStudent::getStudentManagerid, 0);
        studentQueryWrapper.eq(SchoolStudent::getStudentManagerid, 0);
        // 如果有填写特定教师，那就需要是这个教师的学生
        if (manageForm.getTeacher() != null) {
            studentUpdateWrapper.eq(SchoolStudent::getStudentTeacher, manageForm.getTeacher());
            studentQueryWrapper.eq(SchoolStudent::getStudentTeacher, manageForm.getTeacher());
        }

        // ========= 开始排考场 =========
        // 寻找空闲考场
        // 先查询所有考场

//        List<SchoolClassroom> classrooms = nschoolClassroomService.list();
        List<SchoolClassroom> classrooms = schoolClassroomService.selectSchoolClassroomList(null);
        // 遍历考场，把每个考场带入安排表，寻找非冲突的考场
        for (SchoolClassroom classroom : classrooms) {

            studentQueryWrapper.eq(SchoolStudent::getStudentCampus, classroom.getClassroomCampus());
            // 查询这一批的学生人数
            int studentCount = (int) nschoolStudentService.count(studentQueryWrapper);
            if (studentCount > classroom.getClassroomSize()) {
                // 如果人数大于考场容量，那就跳过这个考场
                continue;
            }

            boolean flag = false;

            // 先查询这个考场是否有冲突
            // 获取这个考场的开始结束datetime
            // 先获取考场的安排
            List<SchoolManager> schoolManagers = nschoolManagerService.list(managerQueryWrapper);
            // 遍历安排，把每个安排带入判断
            for (SchoolManager schoolManager : schoolManagers) {
                // 获取安排的开始结束时间
                String managerStart = String.valueOf(schoolManager.getManagerStartdate());
                // 把开始时间加上考试时长加上空闲时间
                Date endDateTime1 = DateUtils.addMinutes(schoolManager.getManagerStartdate(), (int) (schoolManager.getManagerDuration() + manageForm.getRelax()));
                String managerEnd = String.valueOf(endDateTime1);

                // 如果有冲突，那就跳过这个考场
                if (managerStart.compareTo(endDateTime) < 0 && managerEnd.compareTo(startDateTime) > 0) {
                    flag = true;
                }
            }

            // 如果冲突，那就跳过这个考场
            if (flag) {
                continue;
            }


            Integer sid = generateValidateCode(9);


            // 如果不冲突，那就把这个考场分配给学生，这个考场需要是和学生同校区
            studentUpdateWrapper.eq(SchoolStudent::getStudentCampus, sid);
            // 对学生进行分配，先修改学生表
            studentUpdateWrapper.set(SchoolStudent::getStudentManagerid, classroom.getColumnId());
            nschoolStudentService.update(studentUpdateWrapper);
            // 修改安排表
            LambdaUpdateWrapper<SchoolManager> managerUpdateWrapper = new LambdaUpdateWrapper<>();
            managerUpdateWrapper.set(SchoolManager::getManagerStartdate, startDateTime);
            managerUpdateWrapper.set(SchoolManager::getManagerDuration, manageForm.getDuration());
            managerUpdateWrapper.set(SchoolManager::getManagerRegion, classroom.getClassroomCampus());
            managerUpdateWrapper.set(SchoolManager::getManagerClassroom, classroom.getClassroomNumber());
            managerUpdateWrapper.set(SchoolManager::getManagerSubject, subject);
            managerUpdateWrapper.set(SchoolManager::getManagerSid, sid);

            nschoolManagerService.update(managerUpdateWrapper);


        }


        return success();
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
        } else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

}
