<template style="width: 60%">
  <div>
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="考试日期" prop="date">
        <el-date-picker v-model="formData.date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                        :style="{width: '100%'}" placeholder="请选择考试日期" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="考试起止" prop="startend">
        <el-time-picker v-model="formData.startend" is-range format="HH:mm:ss" value-format="HH:mm:ss"
                        :style="{width: '100%'}" start-placeholder="开始时间" end-placeholder="结束时间"
                        range-separator="至"
                        clearable></el-time-picker>
      </el-form-item>
      <el-form-item label="考试时长" prop="duration">
        <el-input v-model="formData.duration" placeholder="请输入考试时长" :maxlength="4" clearable
                  prefix-icon='el-icon-timer' :style="{width: '100%'}">
          <template slot="append">分钟</template>
        </el-input>
      </el-form-item>
      <el-form-item label="考试间隔" prop="relax">
        <el-input v-model="formData.relax" placeholder="请输入考试间隔" :maxlength="4" clearable
                  prefix-icon='el-icon-timer' :style="{width: '100%'}">
          <template slot="append">分钟</template>
        </el-input>
      </el-form-item>
      <el-form-item label="考试课程" prop="subject">
        <el-input v-model="formData.subject" placeholder="请输入考试课程" clearable prefix-icon='el-icon-notebook-1'
                  :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="限定教师" prop="teacher">
        <el-input v-model="formData.teacher" placeholder="限定教师（可选）" clearable prefix-icon='el-icon-s-custom'
                  :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import axios from "axios";
  import request from "@/utils/request";

  export default {
    components: {},
    props: [],
    data() {
      return {
        formData: {
          date: null,
          startend: null,
          duration: undefined,
          relax: undefined,
          subject: undefined,
          teacher: undefined,
        },
        rules: {
          date: [{
            required: true,
            message: '请选择考试日期',
            trigger: 'change'
          }],
          startend: [{
            required: true,
            message: '考试起止不能为空',
            trigger: 'change'
          }],
          duration: [{
            required: true,
            message: '请输入考试时长',
            trigger: 'blur'
          }],
          relax: [{
            required: true,
            message: '请输入考试间隔',
            trigger: 'blur'
          }],
          subject: [{
            required: true,
            message: '请输入考试课程',
            trigger: 'blur'
          }],
          teacher: [],
        },
      }
    },
    computed: {},
    watch: {},
    created() {
    },
    mounted() {
    },
    methods: {
      submitForm() {
        this.$refs['elForm'].validate(valid => {
          if (!valid) return
          // 表单提交
          manage(this.formData).then(response => {
            // 表单提交成功
            console.log(response.data);
            // 执行其他操作，如显示成功消息或重定向到其他页面
          }).catch(error => {
            // 处理错误情况
            console.error(error);
            // 显示错误消息或执行其他错误处理逻辑
          })

          // 示例代码：
          // axios.post('/school/manager', this.formData)
          //   .then(response => {
          //     // 表单提交成功
          //     console.log(response.data);
          //     // 执行其他操作，如显示成功消息或重定向到其他页面
          //   })
          //   .catch(error => {
          //     // 处理错误情况
          //     console.error(error);
          //     // 显示错误消息或执行其他错误处理逻辑
          //
          //   })
        })
      },
      resetForm() {
        this.$refs['elForm'].resetFields()
      },
    }
  }

  export function manage(data) {
    return request({
      url: '/school/manager/manage',
      method: 'post',
      data: data
    })
  }
</script>
<style>
</style>
