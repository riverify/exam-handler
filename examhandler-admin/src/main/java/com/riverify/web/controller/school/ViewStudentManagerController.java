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
import com.riverify.school.domain.ViewStudentManager;
import com.riverify.school.service.IViewStudentManagerService;
import com.riverify.common.utils.poi.ExcelUtil;
import com.riverify.common.core.page.TableDataInfo;

/**
 * viewController
 *
 * @author riverify
 * @date 2023-06-04
 */
@RestController
@RequestMapping("/school/view")
public class ViewStudentManagerController extends BaseController {
    @Autowired
    private IViewStudentManagerService viewStudentManagerService;

    /**
     * 查询view列表
     */
//    @PreAuthorize("@ss.hasPermi('school:view:list')")
    @GetMapping("/list")
    public TableDataInfo list(ViewStudentManager viewStudentManager) {
        startPage();
        List<ViewStudentManager> list = viewStudentManagerService.selectViewStudentManagerList(viewStudentManager);
        return getDataTable(list);
    }

    /**
     * 导出view列表
     */
//    @PreAuthorize("@ss.hasPermi('school:view:export')")
    @Log(title = "view", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ViewStudentManager viewStudentManager) {
        List<ViewStudentManager> list = viewStudentManagerService.selectViewStudentManagerList(viewStudentManager);
        ExcelUtil<ViewStudentManager> util = new ExcelUtil<ViewStudentManager>(ViewStudentManager.class);
        util.exportExcel(response, list, "view数据");
    }

    /**
     * 获取view详细信息
     */
//    @PreAuthorize("@ss.hasPermi('school:view:query')")
    @GetMapping(value = "/{studentId}")
    public AjaxResult getInfo(@PathVariable("studentId") String studentId) {
        return success(viewStudentManagerService.selectViewStudentManagerByStudentId(studentId));
    }

    /**
     * 新增view
     */
//    @PreAuthorize("@ss.hasPermi('school:view:add')")
    @Log(title = "view", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ViewStudentManager viewStudentManager) {
        return toAjax(viewStudentManagerService.insertViewStudentManager(viewStudentManager));
    }

    /**
     * 修改view
     */
//    @PreAuthorize("@ss.hasPermi('school:view:edit')")
    @Log(title = "view", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ViewStudentManager viewStudentManager) {
        return toAjax(viewStudentManagerService.updateViewStudentManager(viewStudentManager));
    }

    /**
     * 删除view
     */
//    @PreAuthorize("@ss.hasPermi('school:view:remove')")
    @Log(title = "view", businessType = BusinessType.DELETE)
    @DeleteMapping("/{studentIds}")
    public AjaxResult remove(@PathVariable String[] studentIds) {
        return toAjax(viewStudentManagerService.deleteViewStudentManagerByStudentIds(studentIds));
    }
}
