<template>
  <div>
    <el-card class="page-card">
      <h2 class="page-title">学生管理</h2>
      <p class="page-desc">支持按学号查询学生信息，并进行新增、修改、删除操作。</p>

      <div class="toolbar">
        <el-input
          v-model="searchId"
          placeholder="请输入学号查询"
          style="width: 220px"
          clearable
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" :loading="loading" @click="handleSearch">查询学生</el-button>
        <el-button type="success" @click="openAddDialog">添加学生</el-button>
      </div>

      <el-empty v-if="!currentStudent && !loading" description="请输入学号查询，或添加新学生" />

      <el-descriptions v-if="currentStudent" :column="2" border title="学生详情">
        <el-descriptions-item label="学号">{{ currentStudent.studentId }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ currentStudent.studentName }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ getGenderLabel(currentStudent.gender) }}</el-descriptions-item>
        <el-descriptions-item label="出生日期">{{ currentStudent.birthDate }}</el-descriptions-item>
        <el-descriptions-item label="班级ID">{{ currentStudent.classId }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ currentStudent.phone }}</el-descriptions-item>
      </el-descriptions>

      <div v-if="currentStudent" class="action-bar">
        <el-button type="warning" @click="openEditDialog">修改信息</el-button>
        <el-button type="danger" @click="handleDelete">删除学生</el-button>
      </div>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogMode === 'add' ? '添加学生' : '修改学生'"
      width="520px"
      destroy-on-close
    >
      <StudentForm ref="studentFormRef" :mode="dialogMode" :model-value="editingStudent" />
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import StudentForm from '../components/StudentForm.vue'
import { addStudent, deleteStudent, getStudent, updateStudent } from '../api/student'
import { getGenderLabel } from '../constants/courses'

const searchId = ref('')
const loading = ref(false)
const submitLoading = ref(false)
const currentStudent = ref(null)
const dialogVisible = ref(false)
const dialogMode = ref('add')
const editingStudent = ref({})
const studentFormRef = ref()

const handleSearch = async () => {
  if (!searchId.value) {
    ElMessage.warning('请输入学号')
    return
  }
  loading.value = true
  try {
    currentStudent.value = await getStudent(Number(searchId.value))
    ElMessage.success('查询成功')
  } catch {
    currentStudent.value = null
  } finally {
    loading.value = false
  }
}

const openAddDialog = () => {
  dialogMode.value = 'add'
  editingStudent.value = {
    studentName: '',
    gender: 0,
    birthDate: '',
    classId: '',
    phone: ''
  }
  dialogVisible.value = true
}

const openEditDialog = () => {
  dialogMode.value = 'edit'
  editingStudent.value = { ...currentStudent.value }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await studentFormRef.value.validate()
  const payload = studentFormRef.value.getPayload()
  submitLoading.value = true
  try {
    if (dialogMode.value === 'add') {
      await addStudent(payload)
      ElMessage.success('添加成功，请使用返回的学号查询学生')
      dialogVisible.value = false
    } else {
      await updateStudent(payload)
      ElMessage.success('修改成功')
      dialogVisible.value = false
      currentStudent.value = await getStudent(payload.studentId)
    }
  } finally {
    submitLoading.value = false
  }
}

const handleDelete = async () => {
  await ElMessageBox.confirm(
    `确定删除学号为 ${currentStudent.value.studentId} 的学生吗？`,
    '删除确认',
    { type: 'warning' }
  )
  await deleteStudent(currentStudent.value.studentId)
  ElMessage.success('删除成功')
  currentStudent.value = null
  searchId.value = ''
}
</script>

<style scoped>
.action-bar {
  margin-top: 16px;
}
</style>
