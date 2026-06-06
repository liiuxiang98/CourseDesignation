<template>
  <el-container class="layout">
    <el-aside width="220px" class="aside">
      <div class="logo">学生成绩管理系统</div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#1f2d3d"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <el-menu-item index="/home">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/students">
          <el-icon><User /></el-icon>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/scores">
          <el-icon><Document /></el-icon>
          <span>成绩管理</span>
        </el-menu-item>
        <el-menu-item index="/chart">
          <el-icon><DataAnalysis /></el-icon>
          <span>成绩图表</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-title">{{ currentTitle }}</div>
        <div class="header-tip">后端地址：http://localhost:8080</div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const activeMenu = computed(() => route.path)
const currentTitle = computed(() => route.meta.title || '学生成绩管理系统')
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.aside {
  background: #1f2d3d;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-weight: 600;
  font-size: 15px;
  border-bottom: 1px solid #263445;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #ebeef5;
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.header-tip {
  font-size: 13px;
  color: #909399;
}

.main {
  padding: 20px;
}
</style>
