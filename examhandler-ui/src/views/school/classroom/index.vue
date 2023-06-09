<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考场号" prop="classroomNumber">
        <el-input
          v-model="queryParams.classroomNumber"
          placeholder="请输入考场号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="可用座位" prop="classroomSize">
        <el-input
          v-model="queryParams.classroomSize"
          placeholder="请输入可用座位"
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
          v-has-role="['admin', 'normal']"
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
          v-has-role="['admin', 'normal']"
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
          v-has-role="['admin', 'normal']"
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
          v-has-role="['admin', 'normal']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classroomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!-- 隐藏主键 -->
      <el-table-column label="考场id主键" align="center" prop="columnId" show-overflow-tooltip="false" v-if="false"/>
      <el-table-column label="考场号" align="center" prop="classroomNumber"/>
      <el-table-column label="所在校区" align="center" prop="classroomCampus"/>
      <el-table-column label="可用座位" align="center" prop="classroomSize"/>
      <el-table-column label="状态" align="center" prop="classroomStatus">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.classroomStatus === 0"
            type="success"
            @click="updateStatus(scope.row)"
            style="cursor: pointer;"
          >
            启用中
          </el-tag>
          <el-tag
            v-else
            type="danger"
            @click="updateStatus(scope.row)"
            style="cursor: pointer;"
          >
            停用中
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-has-role="['admin', 'normal']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-has-role="['admin', 'normal']"
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

    <!-- 添加或修改考场对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考场号" prop="classroomNumber">
          <el-input v-model="form.classroomNumber" placeholder="请输入考场号"/>
        </el-form-item>
        <el-form-item label="所在校区" prop="classroomCampus">
          <el-input v-model="form.classroomCampus" placeholder="请输入所在校区"/>
        </el-form-item>
        <el-form-item label="可用座位" prop="classroomSize">
          <el-input v-model="form.classroomSize" placeholder="请输入可用座位"/>
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
  import {listClassroom, getClassroom, delClassroom, addClassroom, updateClassroom} from "@/api/school/classroom";
  import request from "@/utils/request";

  export default {
    name: "Classroom",
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
        // 考场表格数据
        classroomList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          classroomNumber: null,
          classroomCampus: null,
          classroomSize: null,
          classroomStatus: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          classroomNumber: [
            {required: true, message: "考场号不能为空", trigger: "blur"}
          ],
          classroomCampus: [
            {required: true, message: "所在校区不能为空", trigger: "change"}
          ],
          classroomSize: [
            {required: true, message: "可用座位不能为空", trigger: "blur"}
          ],
          classroomStatus: [
            {required: true, message: "考场状态不能为空", trigger: "change"}
          ]
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询考场列表 */
      getList() {
        this.loading = true;
        listClassroom(this.queryParams).then(response => {
          this.classroomList = response.rows;
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
          columnId: null,
          classroomNumber: null,
          classroomCampus: null,
          classroomSize: null,
          classroomStatus: null
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
        this.ids = selection.map(item => item.columnId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加考场";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const columnId = row.columnId || this.ids
        getClassroom(columnId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改考场";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.columnId != null) {
              updateClassroom(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addClassroom(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 修改状态 */
      updateStatus(row) {
        const classroomId = row.columnId;
        const status = row.classroomStatus === 0 ? 1 : 0;
        return request({
          url: '/school/classroom/classroomStatus/' + classroomId + '/' + status,
          method: 'put'
        }).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.getList();
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const columnIds = row.columnId || this.ids;
        this.$modal.confirm('是否确认删除考场编号为"' + columnIds + '"的数据项？').then(function () {
          return delClassroom(columnIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('school/classroom/export', {
          ...this.queryParams
        }, `classroom_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
