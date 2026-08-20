# 专项 · Java API 熟练度

> 类型：专项缺口 ｜ 触发：Round 4 审计（0297 手写 parser 耗 28min）及多轮反复出现

## 问题定义

反复出现的同一缺口：解题时想不到或写不出标准库的现成接口，被迫手写低层代码或临场回忆方法名。已记录的实例：

- 0297：`split` 未进入候选，手写 `getNum` 解析 token，占据本轮最长耗时的主要部分；
- 偶尔忘记 `Deque` 关键字；
- 忘记队 / 栈的增删方法名；
- 混淆 `put / add / offer / push / pop / poll / peek`。

根因：API 记忆依赖 IDE 补全，白板、限时、出声讲环境下补全不存在。该缺口独立于算法能力，靠刷题不会自动修复，需专项默写。

## 速查表（默写基准）

### Deque 双端队列（栈 / 队列统一容器）

```java
Deque<Integer> dq = new ArrayDeque<>();
// 作栈（LIFO）：push / pop / peek   —— 作用于队头
dq.push(x);  dq.pop();  dq.peek();
// 作队列（FIFO）：offer / poll / peek —— offer 队尾，poll 队头
dq.offer(x); dq.poll(); dq.peek();
// 显式双端：offerFirst / offerLast / pollFirst / pollLast / peekFirst / peekLast
```

记忆锚点：`push/pop` 是栈家族，`offer/poll` 是队列家族，`peek` 通用；LinkedList 同时实现 List 和 Deque，`addFirst/addLast` 用于结果层控制插入方向（0103）。

### PriorityQueue

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0])); // 小根
pq.offer(x); pq.poll(); pq.peek(); pq.isEmpty();
// 大根堆：Comparator 反向或 Collections.reverseOrder()
// 不支持定点删除 → 延迟删除模式
```

### Map

```java
map.put(k, v);  map.get(k);  map.getOrDefault(k, def);  map.remove(k);
map.containsKey(k);  map.merge(k, 1, Integer::sum);       // 计数
map.computeIfAbsent(k, x -> new ArrayList<>()).add(v);    // 邻接表
map.size();  map.entrySet() / keySet() / values();
// TreeMap 范围查询：ceilingKey / floorKey / firstKey / lastKey（0220）
// TreeSet 对应：ceiling / floor / first / last
```

### String / StringBuilder

```java
s.split("/");                 // 正则参数；特殊字符需转义："\\." "\\|"
s.substring(from, to);        // 左闭右开
s.charAt(i);  s.toCharArray();  s.indexOf(x);  s.contains(x);
String.valueOf(num);  Integer.parseInt(s);  String.join(",", list);
StringBuilder sb; sb.append(x); sb.reverse(); sb.deleteCharAt(i); sb.setLength(n); sb.toString();
```

### Arrays / Collections / List

```java
Arrays.sort(arr);  Arrays.sort(arr, cmp);            // 对象数组才能带 cmp
Arrays.fill(arr, v);  Arrays.copyOfRange(arr, from, to);
Collections.sort(list);  Collections.reverse(list);
list.add(x); list.get(i); list.set(i, x); list.remove(list.size() - 1);
new ArrayList<>(map.values());
```

## 混淆高发区

| 方法 | 属于 | 语义 | 失败行为 |
|---|---|---|---|
| `add` / `remove` / `element` | Queue（Collection 家族） | 增 / 删 / 看队头 | 抛异常 |
| `offer` / `poll` / `peek` | Queue | 同上 | 返回 false / null |
| `push` / `pop` / `peek` | Deque 作栈 | 压 / 弹 / 看栈顶 | 抛异常 |
| `put` | Map 专属 | 建立映射 | — |

规则：刷题统一用 `offer / poll / peek`（不抛异常家族）+ 栈场景 `push / pop`；`add/put` 混淆时默念"put 只属于 Map"。

## 训练制度

1. **每周一次 5 分钟默写**：无 IDE 环境手写上表任一分区，错一处即下周重默同区。
2. **AUDIT / MOCK 延续"不查 API"规则**：查了即记一处 API 失分，按区归类到本表。
3. **解析类题起手先问**："这个格式能不能 `split`？"手写 parser 仅在题目明确禁止时使用。
4. R14（String 算法轮）已含"训 Java String API 肌肉记忆"目标，与本表配合执行。
