<template>
  <div>
    <el-card class="page-card">
      <h2 class="page-title">欢迎使用学生成绩管理系统</h2>
      <p class="page-desc">
        前端基于 Vue 3 + Element Plus，后端基于 Spring Boot + MyBatis + MySQL，可直接联调。
      </p>

      <el-row :gutter="16">
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="entry-card" @click="$router.push('/students')">
            <el-icon size="28" color="#409eff"><User /></el-icon>
            <div class="entry-title">学生管理</div>
            <div class="entry-desc">添加、查询、修改、删除学生</div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="entry-card" @click="$router.push('/scores')">
            <el-icon size="28" color="#67c23a"><Document /></el-icon>
            <div class="entry-title">成绩管理</div>
            <div class="entry-desc">录入成绩并按学号/姓名查询</div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="entry-card" @click="$router.push('/chart')">
            <el-icon size="28" color="#e6a23c"><DataAnalysis /></el-icon>
            <div class="entry-title">成绩图表</div>
            <div class="entry-desc">查看单个学生的成绩柱状图</div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="entry-card info-card">
            <el-icon size="28" color="#909399"><Connection /></el-icon>
            <div class="entry-title">联调说明</div>
            <div class="entry-desc">先启动 MySQL 与后端，再启动本前端</div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="page-card">
      <template #header>
        <span>启动检查清单</span>
      </template>
      <el-steps :active="4" align-center finish-status="success">
        <el-step title="安装 Node.js" description="建议 18 或以上版本" />
        <el-step title="启动 MySQL" description="数据库 liuxianglitian" />
        <el-step title="启动后端" description="端口 8080" />
        <el-step title="启动前端" description="端口 5173" />
      </el-steps>
    </el-card>
  </div>
</template>

<style scoped>
.entry-card {
  cursor: pointer;
  margin-bottom: 16px;
  min-height: 140px;
}

.info-card {
  cursor: default;
}

.entry-title {
  margin-top: 12px;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.entry-desc {
  margin-top: 8px;
  font-size: 13px;
  color: #909399;
  line-height: 1.5;
}
</style>
