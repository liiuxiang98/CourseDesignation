import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layout/MainLayout.vue'

const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'students',
        name: 'Students',
        component: () => import('../views/StudentManage.vue'),
        meta: { title: '学生管理' }
      },
      {
        path: 'scores',
        name: 'Scores',
        component: () => import('../views/ScoreManage.vue'),
        meta: { title: '成绩管理' }
      },
      {
        path: 'chart',
        name: 'Chart',
        component: () => import('../views/ScoreChart.vue'),
        meta: { title: '成绩图表' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
