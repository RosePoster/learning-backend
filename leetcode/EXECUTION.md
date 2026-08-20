# EXECUTION.md

# Execution Rules

## Purpose
Keep roadmap progression clear, minimal, and sustainable.

## Rules
1. `ROADMAP.md` is the single source of truth for problem progression.
2. Solved problems are marked as `[x]`; unsolved problems are marked as `[ ]`.
3. New problems move forward through `ROADMAP.md`.
4. Review is required in parallel with new problem solving.
5. High-frequency, error-prone, and design-heavy problems have higher review priority.
6. Problem count is not the goal; interview-ready recall and implementation are.

## Priority
high-frequency coverage > stable recall > clean implementation > raw volume

## NEW / MOCK 起手 Checklist（2026-07-18 中期复盘固化，来源 round-04 / round-05）

1. 读题：圈出选择约束（连续 / 任意子集）与数据范围，再动手。
2. 方案成立后、动手前：哪些状态可降级（历史值 / 上界够不够）？个人变体的证明成本是否高于主流写法？
3. 写单调结构前：相等元素归谁？一边严格、一边非严格，先论证再写符号。
4. 卡边界时：画结构、写等式，不信任脑内模拟。

## 支线制度（与主线同等刚性）

- UNSEEN：每 1-2 周一场，结果记入 ROADMAP 的 UNSEEN 执行日志；无日志视同未执行。
- Java API / 实现层默写：每周日第一题前 5 分钟，结果记入 `insight/java-api-fluency.md` 尾部日志；分区含 API、位运算积木、数值表达式（MOD / long）、三向切分模板。
- R9 启动前：`[r]` 清零 + 建最小测试脚手架（main 模板，服务 MOCK 自造反例与 ACM 笔试 IO）。