import { test, expect } from '@playwright/test'

async function addGrade(page, { studentId, courseLabel, score }) {
  await page.goto('/scores')
  await page.getByRole('tab', { name: '录入成绩' }).click()

  const form = page.locator('.el-form').first()
  await form.locator('.el-input-number input').first().fill(String(studentId))
  await form.locator('.el-select').click()
  await page.getByRole('option', { name: new RegExp(courseLabel) }).click()
  await form.locator('.el-input-number input').nth(1).fill(String(score))
  await page.getByRole('button', { name: '提交成绩' }).click()
  await page.waitForTimeout(500)
}

test.describe('学生成绩管理系统 E2E', () => {
  test('首页与侧边栏导航', async ({ page }) => {
    await page.goto('/home')
    await expect(page.getByText('欢迎使用学生成绩管理系统')).toBeVisible()
    await page.getByRole('menuitem', { name: '学生管理' }).click()
    await expect(page.getByRole('heading', { name: '学生管理' })).toBeVisible()
    await page.getByRole('menuitem', { name: '成绩管理' }).click()
    await expect(page.getByRole('heading', { name: '成绩管理' })).toBeVisible()
    await page.getByRole('menuitem', { name: '成绩图表' }).click()
    await expect(page.getByRole('heading', { name: '成绩图表' })).toBeVisible()
  })

  test('按学号查询种子学生', async ({ page }) => {
    await page.goto('/students')
    await page.getByPlaceholder('请输入学号查询').fill('1000')
    await page.getByRole('button', { name: '查询学生' }).click()
    await expect(page.locator('.el-descriptions')).toContainText('1000')
  })

  test('录入成绩并按学号查询', async ({ page }) => {
    await addGrade(page, { studentId: 1000, courseLabel: '高等数学', score: 90 })

    await page.getByRole('tab', { name: '按学号查询' }).click()
    await page.locator('.toolbar .el-input-number input').fill('1000')
    await page.getByRole('button', { name: '查询' }).click()

    const queryPanel = page.getByRole('tabpanel', { name: '按学号查询' })
    await expect(queryPanel.locator('.el-table__body')).toContainText('90')
    await expect(queryPanel.locator('.el-table__body tr').first()).toBeVisible()
  })

  test('生成成绩柱状图', async ({ page }) => {
    await addGrade(page, { studentId: 1000, courseLabel: '英语', score: 85 })

    await page.goto('/chart')
    await page.locator('.toolbar .el-input-number input').fill('1000')
    await page.getByRole('button', { name: '生成图表' }).click()
    await expect(page.locator('.chart-box')).toBeVisible({ timeout: 15_000 })
    await expect(page.locator('canvas')).toBeVisible()
  })
})
