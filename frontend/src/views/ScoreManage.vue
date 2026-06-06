<template>
  <div>
    <el-card class="page-card">
      <h2 class="page-title">成绩管理</h2>
      <p class="page-desc">录入学生成绩，并支持按学号或姓名查询成绩记录。</p>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="录入成绩" name="add">
          <el-form
            ref="gradeFormRef"
            :model="gradeForm"
            :rules="gradeRules"
            label-width="90px"
            style="max-width: 520px"
          >
            <el-form-item label="学号" prop="studentId">
              <el-input-number v-model="gradeForm.studentId" :min="1" style="width: 100%" />
            </el-form-item>
            <el-form-item label="课程" prop="courseId">
              <el-select v-model="gradeForm.courseId" placeholder="请选择课程" style="width: 100%">
                <el-option
                  v-for="item in COURSE_OPTIONS"
                  :key="item.courseId"
                  :label="`${item.courseId} - ${item.courseName}`"
                  :value="item.courseId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="成绩" prop="score">
              <el-input-number
                v-model="gradeForm.score"
                :min="0"
                :max="100"
                :precision="2"
                :step="0.5"
                style="width: 100%"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="addLoading" @click="handleAddGrade">提交成绩</el-button>
              <el-button @click="resetGradeForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="按学号查询" name="byId">
          <div class="toolbar">
            <el-input-number v-model="queryStudentId" :min="1" placeholder="学号" />
            <el-button type="primary" :loading="queryByIdLoading" @click="handleQueryById">查询</el-button>
          </div>
          <el-table :data="gradeByIdList" border stripe empty-text="暂无成绩数据">
            <el-table-column prop="courseName" label="课程名称" />
            <el-table-column prop="score" label="成绩" />
          </el-table>
        </el-tab-pane>

        <el-tab-pane label="按姓名查询" name="byName">
          <div class="toolbar">
            <el-input
              v-model="queryStudentName"
              placeholder="请输入学生姓名（支持模糊查询）"
              style="width: 280px"
              clearable
              @keyup.enter="handleQueryByName"
            />
            <el-button type="primary" :loading="queryByNameLoading" @click="handleQueryByName">查询</el-button>
          </div>
          <el-table :data="gradeByNameList" border stripe empty-text="暂无成绩数据">
            <el-table-column prop="studentId" label="学号" width="120" />
            <el-table-column label="课程名称" min-width="160">
              <template #default="{ row }">
                {{ getCourseName(row.courseId) }}
              </template>
            </el-table-column>
            <el-table-column prop="courseId" label="课程ID" width="100" />
            <el-table-column prop="score" label="成绩" width="120" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addGrade, getGradeByStudentId, getGradeByStudentName } from '../api/student'
import { COURSE_OPTIONS, getCourseName } from '../constants/courses'

const activeTab = ref('add')
const gradeFormRef = ref()
const addLoading = ref(false)
const queryByIdLoading = ref(false)
const queryByNameLoading = ref(false)
const queryStudentId = ref(null)
const queryStudentName = ref('')
const gradeByIdList = ref([])
const gradeByNameList = ref([])

const gradeForm = reactive({
  studentId: null,
  courseId: null,
  score: null
})

const gradeRules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  courseId: [{ required: true, message: '请选择课程', trigger: 'change' }],
  score: [{ required: true, message: '请输入成绩', trigger: 'blur' }]
}

const resetGradeForm = () => {
  gradeForm.studentId = null
  gradeForm.courseId = null
  gradeForm.score = null
  gradeFormRef.value?.clearValidate()
}

const handleAddGrade = async () => {
  await gradeFormRef.value.validate()
  addLoading.value = true
  try {
    await addGrade({
      studentId: gradeForm.studentId,
      courseId: gradeForm.courseId,
      score: gradeForm.score
    })
    ElMessage.success('成绩录入成功')
    resetGradeForm()
  } finally {
    addLoading.value = false
  }
}

const handleQueryById = async () => {
  if (!queryStudentId.value) {
    ElMessage.warning('请输入学号')
    return
  }
  queryByIdLoading.value = true
  try {
    gradeByIdList.value = (await getGradeByStudentId(queryStudentId.value)) || []
  } catch {
    gradeByIdList.value = []
  } finally {
    queryByIdLoading.value = false
  }
}

const handleQueryByName = async () => {
  if (!queryStudentName.value.trim()) {
    ElMessage.warning('请输入学生姓名')
    return
  }
  queryByNameLoading.value = true
  try {
    gradeByNameList.value = (await getGradeByStudentName(queryStudentName.value.trim())) || []
  } catch {
    gradeByNameList.value = []
  } finally {
    queryByNameLoading.value = false
  }
}
</script>
