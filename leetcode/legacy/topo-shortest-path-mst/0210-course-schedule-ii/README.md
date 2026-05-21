# 210. Course Schedule II

## 我的思考
本题是 207 的拓扑排序输出版本。建立 `pre -> course` 的有向边，并统计每个节点入度。使用 Kahn BFS：所有入度为 0 的课程先入队，出队后削减后继课程入度，入度变为 0 的课程继续入队。若最终处理课程数等于 `numCourses`，返回拓扑序；否则说明存在环，返回空数组。

## 卡点
- 邻接表数组初始化时写错：不能写 `new ArrayList<>()[numCourses]`，数组创建应写成 `new ArrayList[numCourses]`。
- `new ArrayList[numCourses]` 只创建了外层数组，数组元素初始值仍为 `null`。必须逐个执行 `graph[i] = new ArrayList<>()`，否则 `graph[e[1]].add(e[0])` 会空指针异常。

## 关键点
- 边方向为 `prerequisite -> course`。
- Kahn 算法通过入度为 0 的节点逐层消除依赖。
- 若处理节点数不足 `numCourses`，说明存在环。
- 建议统一在出队时写入答案，语义更清楚。

## 复杂度
- 时间：`O(V + E)`
- 空间：`O(V + E)`

## 注意
- Java 中 `List<Integer>[] graph = new ArrayList[numCourses];` 是刷题中常见写法，会有泛型 unchecked warning，但可以接受。
- 邻接表数组的固定模板：先创建数组，再初始化每个桶。