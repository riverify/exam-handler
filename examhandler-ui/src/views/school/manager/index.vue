<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="考试人数" prop="managerStudents">
        <el-input
          v-model="queryParams.managerStudents"
          placeholder="请输入考试人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开考时间" prop="managerStartdate">
        <el-input
          v-model="queryParams.managerStartdate"
          placeholder="请输入开考时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考试时长" prop="managerDuration">
        <el-input
          v-model="queryParams.managerDuration"
          placeholder="请输入考试时长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考试课程" prop="managerSubject">
        <el-input
          v-model="queryParams.managerSubject"
          placeholder="请输入考试课程"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>

      <!-- todo:当前情况 -->

    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="manage"
        >安排
        </el-button>
      </el-col>
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

    <el-table v-loading="loading" :data="managerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="考场编号" align="center" prop="managerId"/>-->
      <!--      <el-table-column label="考场号" align="center" prop="managerSid"/>-->
      <el-table-column label="考场校区" align="center" prop="managerRegion"/>
      <el-table-column label="考场教室" align="center" prop="managerClassroom"/>
      <el-table-column label="考试人数" align="center" prop="managerStudents"/>
      <el-table-column label="开考时间" align="center" prop="managerStartdate"/>
      <el-table-column label="考试时长" align="center" prop="managerDuration"/>
      <el-table-column label="考试课程" align="center" prop="managerSubject"/>
      <!--      <el-table-column label="学科类型" align="center" prop="managerType"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改考场安排对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="考场号" prop="managerSid">-->
        <!--          <el-input v-model="form.managerSid" placeholder="请输入准考证号"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="考场校区" prop="managerRegion">-->
        <!--          <el-input v-model="form.managerRegion" placeholder="请输入考场校区"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="考场教室" prop="managerClassroom">
          <el-input v-model="form.managerClassroom" placeholder="请输入考场教室"/>
        </el-form-item>
        <el-form-item label="考试人数" prop="managerStudents">
          <el-input v-model="form.managerStudents" placeholder="请输入考试人数"/>
        </el-form-item>
        <el-form-item label="开考时间" prop="managerStartdate">
          <el-input v-model="form.managerStartdate" placeholder="请输入开考时间"/>
        </el-form-item>
        <el-form-item label="考试时长" prop="managerDuration">
          <el-input v-model="form.managerDuration" placeholder="请输入考试时长"/>
        </el-form-item>
        <el-form-item label="考试课程" prop="managerSubject">
          <el-input v-model="form.managerSubject" placeholder="请输入考试课程"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import {listManager, getManager, delManager, addManager, updateManager} from "@/api/school/manager";

  export default {
    name: "Manager",
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
        // 考场安排表格数据
        managerList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          managerSid: null,
          managerRegion: null,
          managerClassroom: null,
          managerStudents: null,
          managerStartdate: null,
          managerDuration: null,
          managerSubject: null,
          managerType: null
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
      /** 查询考场安排列表 */
      getList() {
        this.loading = true;
        listManager(this.queryParams).then(response => {
          this.managerList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 添加考场安排 */
      manage() {
        this.$router.push("/school/manager/manage");
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          managerId: null,
          managerSid: null,
          managerRegion: null,
          managerClassroom: null,
          managerStudents: null,
          managerStartdate: null,
          managerDuration: null,
          managerSubject: null,
          managerType: null
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
        this.ids = selection.map(item => item.managerId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加考场安排";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const managerId = row.managerId || this.ids
        getManager(managerId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改考场安排";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.managerId != null) {
              updateManager(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addManager(this.form).then(response => {
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
        const managerIds = row.managerId || this.ids;
        this.$modal.confirm('是否确认删除考场安排编号为"' + managerIds + '"的数据项？').then(function () {
          return delManager(managerIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('school/manager/export', {
          ...this.queryParams
        }, `manager_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
