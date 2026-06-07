import { defineConfig, devices } from '@playwright/test'
import path from 'path'
import { fileURLToPath } from 'url'

const __dirname = path.dirname(fileURLToPath(import.meta.url))
const rootDir = path.resolve(__dirname, '..')

// 本地开发：请手动启动后端(8080)和前端(5173)，E2E 只复用已有服务
// CI 环境：自动启动 test 配置后端 + 前端
const webServer = process.env.CI
  ? [
      {
        command: 'mvn spring-boot:run -Dspring-boot.run.profiles=test',
        url: 'http://localhost:8080/getStudent?studentId=1000',
        cwd: rootDir,
        timeout: 300_000
      },
      {
        command: 'npm run dev',
        url: 'http://localhost:5173',
        timeout: 120_000
      }
    ]
  : [
      {
        command: 'npm run dev',
        url: 'http://localhost:5173',
        reuseExistingServer: true,
        timeout: 60_000
      }
    ]

export default defineConfig({
  testDir: './e2e',
  timeout: 60_000,
  expect: { timeout: 10_000 },
  fullyParallel: false,
  retries: process.env.CI ? 1 : 0,
  workers: 1,
  reporter: [['html', { open: 'never' }], ['list']],
  use: {
    baseURL: 'http://localhost:5173',
    trace: 'on-first-retry',
    screenshot: 'only-on-failure'
  },
  projects: [{ name: 'chromium', use: { ...devices['Desktop Chrome'] } }],
  webServer
})
