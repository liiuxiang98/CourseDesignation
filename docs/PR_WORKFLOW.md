# 分支保护与 Pull Request 流程

本项目采用接近企业团队的协作方式：**禁止直接向 `main` 推送业务改动**，通过 **Pull Request（PR）+ CI 门禁** 合并代码。

## 一、在 GitHub 开启分支保护（一次性配置）

> 分支保护在 GitHub 仓库设置里配置，不在代码仓库内。按下面步骤操作即可。

1. 打开仓库：[https://github.com/liiuxiang98/CourseDesignation](https://github.com/liiuxiang98/CourseDesignation)
2. 进入 **Settings** → **Branches** → **Add branch ruleset**（或 **Add classic branch protection rule**）
3. 保护分支填：`main`
4. 勾选以下规则（推荐）：

| 规则 | 说明 |
|------|------|
| **Require a pull request before merging** | 必须先提 PR 才能合并 |
| **Require approvals** | 个人项目可设为 `0`；团队项目通常 ≥ 1 |
| **Require status checks to pass before merging** | **CI 必须全绿** |
| **Require branches to be up to date before merging** | 合并前需与 `main` 同步（可选，更严格） |
| **Do not allow bypassing the above settings** | 管理员也遵守规则（推荐） |

5. 在 **Status checks that are required** 中搜索并勾选（名称以你仓库 Actions 页为准，一般为）：

- `Backend Tests`
- `Frontend Build`

若搜不到：先随便提一个 PR 让 CI 跑一遍，再回到设置页勾选刚出现的检查项。

6. 保存规则。

### 可选：禁止直接 push 到 main

在同一规则页可勾选 **Restrict who can push to matching branches**，不授权任何人直接 push，强制走 PR。

---

## 二、日常开发流程（Git Flow 简化版）

```text
main（受保护，只通过 PR 合并）
  ↑
  └── feature/xxx（功能分支，在此开发）
```

### 1. 从 main 拉最新代码并建分支

```powershell
git checkout main
git pull origin main
git checkout -b feature/你的功能名
```

分支命名建议：

- `feature/add-score-export` — 新功能
- `fix/student-update-sql` — Bug 修复
- `test/add-api-cases` — 测试相关
- `ci/improve-workflow` — CI 改动

### 2. 开发并本地自测

```powershell
# 后端接口测试
mvn test

# 前端构建（改了前端时）
cd frontend
npm run build
cd ..
```

### 3. 提交并推到远程功能分支

```powershell
git add .
git commit -m "feat: 简要说明本次改动"
git push -u origin feature/你的功能名
```

### 4. 在 GitHub 创建 Pull Request

1. 推送后页面会出现 **Compare & pull request**，或到 **Pull requests** → **New pull request**
2. **base** 选 `main`，**compare** 选你的 `feature/xxx`
3. 按 PR 模板填写说明
4. 创建 PR 后，**CI 自动运行**（`Backend Tests` + `Frontend Build`）

### 5. 等待 CI 通过

- 全部 ✅：可以合并
- 有 ❌：点进失败 Job 看日志 → 本地修复 → 再 `commit` + `push` 到同一分支，PR 会自动重跑 CI

### 6. 合并 PR

- 点击 **Merge pull request** → **Confirm merge**
- 合并后在本地同步：

```powershell
git checkout main
git pull origin main
git branch -d feature/你的功能名
```

---

## 三、CI 与分支保护如何配合

| 事件 | CI 行为 |
|------|---------|
| 向 `main` 提 PR | 自动跑 `ci.yml`：后端测试 + 前端构建 |
| PR 更新（新 push） | 重新跑 CI；旧运行会被取消（concurrency） |
| 直接 push 到 `main` | 也会跑 CI，但开启分支保护后应改为只通过 PR 合并 |
| E2E | 独立流水线 `e2e.yml`，手动或定时，不阻塞每次 PR |

---

## 四、演示「CI 门禁」的练手方式

想体验企业里「CI 红了不能合」的流程：

1. 建分支 `test/ci-gate-demo`
2. 故意改坏一条测试断言
3. 提 PR → 观察 CI 失败、合并按钮被禁用
4. 修复测试 → push → CI 变绿 → 合并
5. 演示完可关闭 PR 不合并，或合并后 revert

---

## 五、与 Jenkins 企业的对应关系

| 企业（Jenkins）概念 | 本项目（GitHub） |
|---------------------|------------------|
| 保护主干分支 | Branch protection on `main` |
| 合并前流水线通过 | Required status checks |
| Code Review | Pull Request +（可选）Required approvals |
| 构建产物 | Actions Artifacts（Allure / Playwright 报告） |

平台不同，**流程一致**：功能分支 → PR → 自动化检查 → 合并。
