<template>
  <div>
    <el-card class="page-card">
      <h2 class="page-title">成绩图表</h2>
      <p class="page-desc">输入学号后，展示该学生各课程成绩的柱状图。</p>

      <div class="toolbar">
        <el-input-number v-model="studentId" :min="1" placeholder="请输入学号" />
        <el-button type="primary" :loading="loading" @click="handleLoadChart">生成图表</el-button>
      </div>

      <el-empty v-if="!chartData.length && !loading" description="请输入学号并生成图表" />

      <ScoreBarChart
        v-if="chartData.length"
        :data="chartData"
        :student-name="studentName"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import ScoreBarChart from '../components/ScoreBarChart.vue'
import { getGradeChart } from '../api/student'

const studentId = ref(null)
const loading = ref(false)
const chartData = ref([])
const studentName = ref('')

const handleLoadChart = async () => {
  if (!studentId.value) {
    ElMessage.warning('请输入学号')
    return
  }
  loading.value = true
  try {
    const data = (await getGradeChart(studentId.value)) || []
    chartData.value = data
    studentName.value = data[0]?.studentName || ''
    if (!data.length) {
      ElMessage.info('该学生暂无成绩数据')
    }
  } catch {
    chartData.value = []
    studentName.value = ''
  } finally {
    loading.value = false
  }
}
</script>
