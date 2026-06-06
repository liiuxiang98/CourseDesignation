<template>
  <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
    <el-form-item label="姓名" prop="studentName">
      <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-radio-group v-model="form.gender">
        <el-radio :label="0">男</el-radio>
        <el-radio :label="1">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="出生日期" prop="birthDate">
      <el-date-picker
        v-model="form.birthDate"
        type="date"
        value-format="YYYY-MM-DD"
        placeholder="请选择出生日期"
        style="width: 100%"
      />
    </el-form-item>
    <el-form-item label="班级ID" prop="classId">
      <el-input v-model="form.classId" placeholder="例如：101" />
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入手机号" />
    </el-form-item>
    <el-form-item v-if="mode === 'edit'" label="学号">
      <el-input v-model="form.studentId" disabled />
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive, ref, watch } from 'vue'

const props = defineProps({
  mode: {
    type: String,
    default: 'add'
  },
  modelValue: {
    type: Object,
    default: () => ({})
  }
})

const formRef = ref()
const form = reactive({
  studentId: null,
  studentName: '',
  gender: 0,
  birthDate: '',
  classId: '',
  phone: ''
})

const rules = {
  studentName: [{ required: true, message: '请输入学生姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  birthDate: [{ required: true, message: '请选择出生日期', trigger: 'change' }],
  classId: [{ required: true, message: '请输入班级ID', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '请输入11位手机号', trigger: 'blur' }
  ]
}

watch(
  () => props.modelValue,
  (value) => {
    Object.assign(form, {
      studentId: value.studentId ?? null,
      studentName: value.studentName ?? '',
      gender: value.gender ?? 0,
      birthDate: value.birthDate ?? '',
      classId: value.classId != null ? String(value.classId) : '',
      phone: value.phone ?? ''
    })
  },
  { immediate: true, deep: true }
)

const validate = () => formRef.value.validate()

const getPayload = () => ({
  studentId: form.studentId,
  studentName: form.studentName,
  gender: form.gender,
  birthDate: form.birthDate,
  classId: form.classId,
  phone: form.phone
})

defineExpose({ validate, getPayload })
</script>
