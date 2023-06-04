<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专业名称" prop="studentMajor">
        <el-input
          v-model="queryParams.studentMajor"
          placeholder="请输入专业名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程名称" prop="studentClassname">
        <el-input
          v-model="queryParams.studentClassname"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师" prop="studentTeacher">
        <el-input
          v-model="queryParams.studentTeacher"
          placeholder="请输入教师"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考场校区" prop="managerRegion">
        <el-input
          v-model="queryParams.managerRegion"
          placeholder="请输入考场校区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考场教室" prop="managerClassroom">
        <el-input
          v-model="queryParams.managerClassroom"
          placeholder="请输入考场教室"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开考时间" prop="managerStartdate">
        <el-date-picker clearable
                        v-model="queryParams.managerStartdate"
                        type="date"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择开考时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="考试时长" prop="managerDuration">
        <el-input
          v-model="queryParams.managerDuration"
          placeholder="请输入考试时长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="viewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="学号" align="center" prop="studentId"/>
      <el-table-column label="姓名" align="center" prop="studentName"/>
      <el-table-column label="专业名称" align="center" prop="studentMajor"/>
      <el-table-column label="课程名称" align="center" prop="studentClassname"/>
      <el-table-column label="教师" align="center" prop="studentTeacher"/>
      <el-table-column label="考场校区" align="center" prop="managerRegion"/>
      <el-table-column label="考场教室" align="center" prop="managerClassroom"/>
      <el-table-column label="开考时间" align="center" prop="managerStartdate"/>
      <el-table-column label="考试时长" align="center" prop="managerDuration"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改view对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listView, getView, delView, addView, updateView} from "@/api/school/view";

  export default {
    name: "View",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // view表格数据
        viewList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          studentId: null,
          studentName: null,
          studentMajor: null,
          studentClassname: null,
          studentTeacher: null,
          managerRegion: null,
          managerClassroom: null,
          managerStartdate: null,
          managerDuration: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询view列表 */
      getList() {
        this.loading = true;
        listView(this.queryParams).then(response => {
          this.viewList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          studentId: null,
          studentName: null,
          studentMajor: null,
          studentClassname: null,
          studentTeacher: null,
          managerRegion: null,
          managerClassroom: null,
          managerStartdate: null,
          managerDuration: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.studentId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加view";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const studentId = row.studentId || this.ids
        getView(studentId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改view";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.studentId != null) {
              updateView(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addView(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const studentIds = row.studentId || this.ids;
        this.$modal.confirm('是否确认删除view编号为"' + studentIds + '"的数据项？').then(function () {
          return delView(studentIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('school/view/export', {
          ...this.queryParams
        }, `view_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
