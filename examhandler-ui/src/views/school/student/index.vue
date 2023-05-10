<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
          v-has-role="['admin', 'normal']"
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
      <el-form-item label="学科类别" prop="studentCategory">
        <el-input
          v-model="queryParams.studentCategory"
          placeholder="请输入学科类别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校区" prop="studentCampus">
        <el-input
          v-model="queryParams.studentCampus"
          placeholder="请输入校区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="星期几" prop="studentDay">
        <el-input
          v-model="queryParams.studentDay"
          placeholder="请输入星期几"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第几节" prop="studentSession">
        <el-input
          v-model="queryParams.studentSession"
          placeholder="请输入第几节"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上机地点" prop="studentClassroom">
        <el-input
          v-model="queryParams.studentClassroom"
          placeholder="请输入上机课地点"
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
        >新增</el-button>
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
        >修改</el-button>
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
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-has-role="['admin', 'normal']"
        >导入
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-pie-chart"-->
      <!--          size="mini"-->
      <!--          @click="handleCount"-->
      <!--          v-hasPermi="['school:student:export']"-->
      <!--        >统计-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学号" align="center" prop="studentId"/>
      <el-table-column label="姓名" align="center" prop="studentName"/>
      <el-table-column label="专业名称" align="center" prop="studentMajor"/>
      <el-table-column label="课程名称" align="center" prop="studentClassname"/>
      <el-table-column label="教师" align="center" prop="studentTeacher"/>
      <el-table-column label="学科类别" align="center" prop="studentCategory"/>
      <el-table-column label="校区" align="center" prop="studentCampus"/>
      <el-table-column label="星期几" align="center" prop="studentDay"/>
      <el-table-column label="第几节" align="center" prop="studentSession"/>
      <el-table-column label="上机课地点" align="center" prop="studentClassroom"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.status === '0'"
            type="success"
            @click="updateStatus(scope.row)"
            style="cursor: pointer;"
          >
            正常
          </el-tag>
          <el-tag
            v-else
            type="danger"
            @click="updateStatus(scope.row)"
            style="cursor: pointer;"
          >
            停用
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
          >删除</el-button>
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

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <!-- 修改信息时学号为不可修改的 -->
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号"/>
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="专业名称" prop="studentMajor">
          <el-input v-model="form.studentMajor" placeholder="请输入专业名称"/>
        </el-form-item>
        <el-form-item label="课程名称" prop="studentClassname">
          <el-input v-model="form.studentClassname" placeholder="请输入课程名称"/>
        </el-form-item>
        <el-form-item label="教师" prop="studentTeacher">
          <el-input v-model="form.studentTeacher" placeholder="请输入教师"/>
        </el-form-item>
        <el-form-item label="学科类别" prop="studentCategory">
          <el-input v-model="form.studentCategory" placeholder="请输入学科类别"/>
        </el-form-item>
        <el-form-item label="校区" prop="studentCampus">
          <el-input v-model="form.studentCampus" placeholder="请输入校区"/>
        </el-form-item>
        <el-form-item label="星期几" prop="studentDay">
          <el-input v-model="form.studentDay" placeholder="请输入星期几" />
        </el-form-item>
        <el-form-item label="第几节" prop="studentSession">
          <el-input v-model="form.studentSession" placeholder="请输入第几节" />
        </el-form-item>
        <el-form-item label="上机课地点" prop="studentClassroom">
          <el-input v-model="form.studentClassroom" placeholder="请输入上机课地点"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 学生信息导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listStudent, getStudent, delStudent, addStudent, updateStudent} from "@/api/school/student";
  import {getToken} from "@/utils/auth";
  import request from "@/utils/request";

export default {
  name: "Student",
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
      // 学生信息表格数据
      studentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 导入学生数据
      // 用户导入参数
      upload: {
        // 是否显示弹出层（学生导入）
        open: false,
        // 弹出层标题（学生导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/school/student/importData"  /* important code */
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sid: null,
        studentId: null,
        studentName: null,
        studentMajor: null,
        studentTeacher: null,
        studentCategory: null,
        studentCampus: null,
        studentDay: null,
        studentSession: null,
        studentClassroom: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentId: [
          {required: true, message: "学号不能为空", trigger: "blur"}
        ],
        studentName: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ],
        studentMajor: [
          {required: true, message: "专业名称不能为空", trigger: "blur"}
        ],
        studentClassname: [
          {required: true, message: "课程名称不能为空", trigger: "blur"}
        ],
        studentTeacher: [
          {required: true, message: "教师不能为空", trigger: "blur"}
        ],
        studentCategory: [
          {required: true, message: "学科类别不能为空", trigger: "blur"}
        ],
        studentDay: [
          {required: true, message: "星期几不能为空", trigger: "blur"}
        ],
        studentSession: [
          { required: true, message: "第几节不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学生信息列表 */
    getList() {
      this.loading = true;
      listStudent(this.queryParams).then(response => {
        this.studentList = response.rows;
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
        sid: null,
        studentId: null,
        studentName: null,
        studentMajor: null,
        studentTeacher: null,
        studentCategory: null,
        studentCampus: null,
        studentDay: null,
        studentSession: null,
        studentClassroom: null,
        status: null
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
      this.ids = selection.map(item => item.sid)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学生信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sid = row.sid || this.ids
      getStudent(sid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生信息(无法修改学号)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.status != null) {
            updateStudent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudent(this.form).then(response => {
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
      const sid = row.sid || this.ids
      const status = row.status === '0' ? 1 : 0
      return request({
        url: '/school/student/status/' + sid + '/' + status,
        method: 'put'
      }).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const sid = row.sid || this.ids;
      this.$modal.confirm('是否确认删除选中的数据项？').then(function () {
        return delStudent(sid);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('school/student/export', {
        ...this.queryParams
      }, `student_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "学生导入";
      this.upload.open = true;
    },
    /** 导入模板 */
    importTemplate() {
      this.download('school/student/importTemplate', {}, `student_template.xlsx`)
    },

    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
