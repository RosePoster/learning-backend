# 2115. Find All Possible Recipes from Given Supplies

## 我的思考
本题一开始试图直接基于 `ingredients` 推进，但很快发现 `ingredients` 是 `recipe -> needs` 的方向。某个菜被做出来后，不能直接通过原始 `ingredients` 快速知道哪些菜因此可能可以做了。

因此需要反转依赖，建立 `support`：如果某个 ingredient 本身也是 recipe，那么该 recipe 被做出来后，可以支持所有依赖它的 recipe。同时配合 `inDegree` 统计每个 recipe 还缺多少 ingredient，入度为 0 的 recipe 就可以被制作出来。

主逻辑先按拓扑排序写出：将所有 `inDegree == 0` 的 recipe 入队；每做出一个 recipe，就遍历它支持的 recipe，并减少对应入度；入度归零后继续入队。

实现时，一开始把 `inDegree` 和 `support` 视为已经建立好，先写主逻辑。随后尝试写一个 helper 一次性构建两个结构，并考虑把结构注册为类成员。后来发现 `inDegree` 和 `support` 的初始化并不耦合，于是拆分为两个 builder，结构更清楚。

总耗时约 35min：前 5min 主要用于确定反转依赖和拓扑排序建模，后 30min 主要耗在 builder 组织和代码结构收敛上。

## 卡点
- 原始 `ingredients` 方向不能直接支持拓扑推进，需要反转依赖，建立 `support`。
- `inDegree` 和 `support` 的构建一开始想合并处理，但后来发现两者职责不同，拆成两个 builder 更清晰。
- 主要耗时不在拓扑模型，而在实现组织：是否使用类成员、是否一个 helper 构建全部结构、两个结构是否耦合。

## 关键点
- `inDegree[i]` 表示第 `i` 个 recipe 还缺多少 ingredient。
- supplies 不需要作为图节点，可以在初始化 `inDegree` 时直接抵消已有 ingredient。
- `support[x]` 表示 recipe `x` 做出来后，可以支持哪些 recipe。
- 拓扑推进的核心问题是：当一个资源变得可用时，需要快速找到哪些节点可以因此减少依赖。

## 复杂度
- 时间：`O(R + M + S)`，其中 `R` 为 recipes 数量，`M` 为所有 ingredients 总数，`S` 为 supplies 数量。
- 空间：`O(R + M + S)`。

## 注意
- 绕路主要发生在代码组织层，不是核心建模错误。
- 后续遇到类似拓扑题时，先判断原始依赖方向是否适合推进；如果新增一个“可用资源/已完成节点”后无法快速找到受影响节点，就需要建立反向依赖表。