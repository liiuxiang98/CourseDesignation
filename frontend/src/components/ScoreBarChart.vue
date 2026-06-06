<template>
  <div ref="chartRef" class="chart-box"></div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref, watch } from 'vue'
import * as echarts from 'echarts'

const props = defineProps({
  data: {
    type: Array,
    default: () => []
  },
  studentName: {
    type: String,
    default: ''
  }
})

const chartRef = ref()
let chartInstance = null

const renderChart = () => {
  if (!chartRef.value) return

  if (!chartInstance) {
    chartInstance = echarts.init(chartRef.value)
  }

  const courseNames = props.data.map((item) => item.courseName)
  const scores = props.data.map((item) => Number(item.score))

  chartInstance.setOption({
    title: {
      text: props.studentName ? `${props.studentName} 的成绩柱状图` : '学生成绩柱状图',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: courseNames,
      axisLabel: {
        interval: 0,
        rotate: courseNames.length > 4 ? 20 : 0
      }
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 100,
      name: '分数'
    },
    series: [
      {
        name: '成绩',
        type: 'bar',
        data: scores,
        barMaxWidth: 48,
        itemStyle: {
          color: '#409eff',
          borderRadius: [6, 6, 0, 0]
        },
        label: {
          show: true,
          position: 'top'
        }
      }
    ]
  })
}

const handleResize = () => {
  chartInstance?.resize()
}

watch(
  () => [props.data, props.studentName],
  () => renderChart(),
  { deep: true }
)

onMounted(() => {
  renderChart()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  chartInstance?.dispose()
  chartInstance = null
})
</script>

<style scoped>
.chart-box {
  width: 100%;
  height: 420px;
}
</style>
