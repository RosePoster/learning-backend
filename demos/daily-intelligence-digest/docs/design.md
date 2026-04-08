# Daily Intelligence Digest — 项目设计文档

## 1. 项目目标

构建一个个性化技术情报摘要系统，每天自动生成技术早报，并在周末推荐 1–3 篇深度精读内容。

核心目标：

- 聚合多源技术情报，提供高质量筛选结果
- 通过确定性逻辑处理数据主体，LLM 仅作为处理组件之一

---

## 2. 核心功能范围

Daily Pipeline:

1. news aggregation
2. deduplication
3. summarization
4. ranking
5. relevance scoring
6. daily digest generation

Weekly Pipeline:

- 基于本周聚合数据
- 推荐 1–3 篇深度精读
- 提供推荐理由

---

## 3. 设计原则

- 确定性逻辑优先，LLM 仅处理语义类任务（摘要、标签提取、推荐理由）
- 核心处理逻辑（dedup、ranking、filtering、aggregation、normalization）不依赖 LLM

---

## 4. 输出形式

MVP 输出形式：

- Markdown 日报
- Markdown 周报
- 可选 HTML 渲染，本地查看

MVP 不包含：

- 前端 Dashboard
- 用户系统
- 实时更新
- 复杂 UI

核心价值在于 pipeline 而非 UI。

---

## 5. LLM Client

选型：Gemini CLI（Pro 账号）

使用场景：

- summarization
- tag extraction
- weekly recommendation reasoning
- optional scoring explanation

不用于：

- dedup
- ranking
- filtering
- aggregation
- normalization

原则：deterministic first，LLM second

---

## 6. Gemini CLI 调用边界

允许的调用模式：

- 本地脚本调用 gemini CLI
- headless 模式
- 串行批处理
- 定时任务触发
- 捕获 stdout 输出，本地消费结果

示例调用链：

```
script -> gemini CLI -> stdout -> markdown
```

禁止的调用模式：

- 包装为 HTTP API
- 多用户共享
- 高并发 worker
- daemon 持续运行
- 作为中转服务供其他程序复用

推荐安全运行模式：

```
cron -> fetch -> process -> gemini -> output -> exit
```

---

## 7. 系统整体数据流

每日流程：

```
fetch sources
    ↓
normalize items
    ↓
deduplicate
    ↓
score relevance
    ↓
select top candidates
    ↓
gemini summarize
    ↓
generate daily digest
    ↓
save markdown
```

周末流程：

```
load weekly items
    ↓
rank importance
    ↓
select top 3
    ↓
gemini generate reason
    ↓
generate weekly digest
```

---

## 8. 模块划分

**Source Layer**

- NewsSourceFetcher
- PaperSourceFetcher
- RSSFetcher

**Processing Layer**

- NormalizationService
- DeduplicationService
- RankingService
- RelevanceScoringService

**LLM Layer**

- GeminiCliClient
- SummarizationService
- WeeklyRecommendationService

**Digest Layer**

- DailyDigestGenerator
- WeeklyDigestGenerator
- MarkdownRenderer

**Storage Layer**

- ItemRepository
- DigestRepository

**Scheduler Layer**

- DailyJob
- WeeklyJob

---

## 9. 数据模型

**RawItem**

| 字段 | 类型 |
|------|------|
| id | string |
| source | string |
| title | string |
| url | string |
| content | string |
| publish_time | datetime |

**NormalizedItem**

| 字段 | 类型 |
|------|------|
| id | string |
| title | string |
| summary | string |
| tags | []string |
| source | string |
| score | float |
| created_at | datetime |

**DigestEntry**

| 字段 | 类型 |
|------|------|
| title | string |
| summary | string |
| source | string |
| url | string |
| relevance_score | float |

**WeeklyPick**

| 字段 | 类型 |
|------|------|
| title | string |
| reason | string |
| url | string |
| priority | int |

---

## 10. 关键设计点

### Deduplication Strategy

可选方案：

- title similarity
- url hash
- embedding（后期扩展）
- keyword overlap

MVP：title + source 组合去重

---

### Relevance Scoring

评分维度：

- source weight
- keyword match
- recency
- duplicate count
- topic preference

MVP：基于规则的简单加权打分

---

### Ranking

输入：

- relevance score
- source priority
- freshness

输出：top N 条目

---

## 11. LLM 使用边界

适合 LLM 处理：

- 摘要生成
- 推荐理由生成
- 标签提取
- 简短解释

不适合 LLM 处理：

- 排序
- 去重
- 过滤
- 决策逻辑

---

## 12. MVP 范围

**包含：**

- 每日抓取
- 去重
- 排序
- Gemini 摘要
- Markdown 输出
- 周末推荐

**不包含：**

- UI
- 用户系统
- 实时更新
- 向量数据库
- 多模型支持
- Agent 系统

---

## 13. 项目结构

```
backend/
├── sources/
├── processing/
├── llm/
├── digest/
├── scheduler/
├── storage/
└── model/
```

---

## 14. 开发顺序

1. Source fetch
2. Normalize
3. Deduplication
4. Ranking
5. Gemini client
6. Daily digest generation
7. Weekly pick generation
