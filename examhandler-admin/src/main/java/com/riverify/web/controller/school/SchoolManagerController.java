package com.riverify.web.controller.school;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/school/manager")
public class SchoolManagerController extends BaseController {
    @Autowired
    private ISchoolManagerService schoolManagerService;

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
}
