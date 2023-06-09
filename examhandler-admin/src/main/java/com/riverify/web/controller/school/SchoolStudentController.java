package com.riverify.web.controller.school;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.riverify.common.core.domain.entity.SysUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.riverify.school.domain.SchoolStudent;
import com.riverify.school.service.ISchoolStudentService;
import com.riverify.common.utils.poi.ExcelUtil;
import com.riverify.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生信息Controller
 *
 * @author riverify
 * @date 2023-05-10
 */
@RestController
@RequestMapping("/school/student")
public class SchoolStudentController extends BaseController
{
    @Autowired
    private ISchoolStudentService schoolStudentService;

    /**
     * 查询学生信息列表
     */
//    @PreAuthorize("@ss.hasPermi('school:student:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchoolStudent schoolStudent)
    {
        startPage();
        List<SchoolStudent> list = schoolStudentService.selectSchoolStudentList(schoolStudent);
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
//    @PreAuthorize("@ss.hasPermi('school:student:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SchoolStudent schoolStudent) {
        List<SchoolStudent> list = schoolStudentService.selectSchoolStudentList(schoolStudent);
        ExcelUtil<SchoolStudent> util = new ExcelUtil<SchoolStudent>(SchoolStudent.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 导入学生信息数据
     */
    @Log(title = "学生信息", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SchoolStudent> util = new ExcelUtil<SchoolStudent>(SchoolStudent.class);
        List<SchoolStudent> studentList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = schoolStudentService.importStudent(studentList, updateSupport, operName);
        return success(message);
    }

    /**
     * 获取学生信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('school:student:query')")
    @GetMapping(value = "/{sid}")
    public AjaxResult getInfo(@PathVariable("sid") Long sid) {
        return success(schoolStudentService.selectSchoolStudentBySid(sid));
    }

    /**
     * 新增学生信息
     */
//    @PreAuthorize("@ss.hasPermi('school:student:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchoolStudent schoolStudent)
    {
        schoolStudent.setStudentManagerid(0L);
        return toAjax(schoolStudentService.insertSchoolStudent(schoolStudent));
    }

    /**
     * 修改学生信息
     */
//    @PreAuthorize("@ss.hasPermi('school:student:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchoolStudent schoolStudent)
    {
        return toAjax(schoolStudentService.updateSchoolStudent(schoolStudent));
    }

    /**
     * 删除学生信息
     */
//    @PreAuthorize("@ss.hasPermi('school:student:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sids}")
    public AjaxResult remove(@PathVariable Long[] sids) {
        return toAjax(schoolStudentService.deleteSchoolStudentBySids(sids));
    }


    /**
     * 更新学生状态
     */
    @PutMapping("/status/{sid}/{status}")
    public AjaxResult status(@PathVariable Long sid, @PathVariable String status) {
        return toAjax(schoolStudentService.updateSchoolStudentStatus(sid, status));
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SchoolStudent> util = new ExcelUtil<SchoolStudent>(SchoolStudent.class);
        util.importTemplateExcel(response, "学生信息");
    }
}
