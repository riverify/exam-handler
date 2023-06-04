package com.riverify.school.controller;

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
import com.riverify.school.domain.SchoolClassroom;
import com.riverify.school.service.ISchoolClassroomService;
import com.riverify.common.utils.poi.ExcelUtil;
import com.riverify.common.core.page.TableDataInfo;

/**
 * 考场Controller
 *
 * @author riverify
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/school/classroom")
public class SchoolClassroomController extends BaseController {
    @Autowired
    private ISchoolClassroomService schoolClassroomService;

    /**
     * 查询考场列表
     */
    @PreAuthorize("@ss.hasPermi('school:classroom:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchoolClassroom schoolClassroom) {
        startPage();
        List<SchoolClassroom> list = schoolClassroomService.selectSchoolClassroomList(schoolClassroom);
        return getDataTable(list);
    }

    /**
     * 导出考场列表
     */
//    @PreAuthorize("@ss.hasPermi('school:classroom:export')")
    @Log(title = "考场", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SchoolClassroom schoolClassroom) {
        List<SchoolClassroom> list = schoolClassroomService.selectSchoolClassroomList(schoolClassroom);
        ExcelUtil<SchoolClassroom> util = new ExcelUtil<SchoolClassroom>(SchoolClassroom.class);
        util.exportExcel(response, list, "考场数据");
    }

    /**
     * 获取考场详细信息
     */
//    @PreAuthorize("@ss.hasPermi('school:classroom:query')")
    @GetMapping(value = "/{columnId}")
    public AjaxResult getInfo(@PathVariable("columnId") Long columnId) {
        return success(schoolClassroomService.selectSchoolClassroomByColumnId(columnId));
    }

    /**
     * 新增考场
     */
//    @PreAuthorize("@ss.hasPermi('school:classroom:add')")
    @Log(title = "考场", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchoolClassroom schoolClassroom) {
        return toAjax(schoolClassroomService.insertSchoolClassroom(schoolClassroom));
    }

    /**
     * 修改考场
     */
//    @PreAuthorize("@ss.hasPermi('school:classroom:edit')")
    @Log(title = "考场", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchoolClassroom schoolClassroom) {
        return toAjax(schoolClassroomService.updateSchoolClassroom(schoolClassroom));
    }

    /**
     * 删除考场
     */
//    @PreAuthorize("@ss.hasPermi('school:classroom:remove')")
    @Log(title = "考场", businessType = BusinessType.DELETE)
    @DeleteMapping("/{columnIds}")
    public AjaxResult remove(@PathVariable Long[] columnIds) {
        return toAjax(schoolClassroomService.deleteSchoolClassroomByColumnIds(columnIds));
    }

    /**
     * 更新考场状态
     */
    @PutMapping("/classroomStatus/{classroomId}/{status}")
    public AjaxResult status(@PathVariable Long classroomId, @PathVariable Long status) {
        return toAjax(schoolClassroomService.updateSchoolClassroomStatus(classroomId, status));
    }
}
