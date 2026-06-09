# 380. Insert Delete GetRandom O(1)

## 我的思考

题目要求随机取元素，因此需要一个支持按下标随机访问的结构。

最初想到用 `ArrayList` 记录元素，用 `Set` 记录元素是否存在。插入和删除只改动 `Set`，插入时同时插入 `ArrayList`；删除时不处理 `ArrayList`。随机读取时从 `ArrayList` 中随机取元素，如果读到的元素已经不在 `Set` 中，就顺序向后找下一个有效元素。

这个方案的问题是：`ArrayList` 会一直扩张，空间不受当前集合大小控制；同时 `getRandom` 可能连续跳过多个已删除元素，最坏时间复杂度不是 `O(1)`。

最终采用 `ArrayList + HashMap`：

- `ArrayList` 存储当前集合中的所有元素，支持 `O(1)` 随机下标访问。
- `HashMap` 存储 `val -> val 在 ArrayList 中的下标`，支持 `O(1)` 定位元素。
- 插入时，将元素加入 `ArrayList` 末尾，并在 `HashMap` 中记录其下标。
- 删除时，先找到待删除元素的下标，再用末尾元素覆盖该位置，最后删除 `ArrayList` 末尾元素。
- 随机读取时，生成 `[0, list.size())` 范围内的随机下标，并返回对应元素。

## 卡点

1. `ArrayList` 要通过“尾元素覆盖待删除位置”的方式保证 `O(1)` 删除。

普通 `ArrayList.remove(index)` 会导致后续元素整体前移，时间复杂度是 `O(n)`。  
本题不要求保持元素顺序，因此可以用最后一个元素覆盖待删除位置，再删除最后一个位置，从而将删除控制在 `O(1)`。

2. 出现了两个低级 bug，额外 debug 了约 10 分钟。

第一个错误是更新尾元素下标时写错：

应该将 `lastVal` 的下标更新为 `deleteIndex`，因为它被移动到了待删除元素的位置。  
一开始误写成了 `lastIndex`，但 `lastIndex` 这个位置马上会被删除，不能再作为有效下标。

第二个错误是 `remove` 中 `map.put(lastVal, deleteIndex)` 和 `map.remove(val)` 的顺序写反。

当删除的元素本身就是尾元素时，`val == lastVal`。  
如果先 `map.remove(val)`，再 `map.put(lastVal, deleteIndex)`，会把已经删除的元素重新加入 map，导致结构错误。  
因此统一写法中，需要先更新尾元素下标，再删除目标元素在 map 中的记录。

## 关键点

- `getRandom` 需要 `ArrayList`，因为它支持按随机下标 `O(1)` 访问。
- `remove` 需要 `HashMap`，因为它支持根据 `val` 在 `O(1)` 时间找到数组下标。
- 本题不要求维护元素顺序，因此删除时可以交换顺序。
- 删除中间元素的核心操作是：用尾元素覆盖待删除元素，再删除尾部。
- `HashMap` 中存的是 `val -> index`，删除和交换后必须同步更新 index。
- 使用 `Random.nextInt(list.size())` 可以生成 `[0, list.size())` 范围内的随机下标。

## 复杂度

- 时间：`insert`、`remove`、`getRandom` 均为平均 `O(1)`。
- 空间：`O(n)`。

## 注意

- 采用统一删除写法，`map.put(lastVal, deleteIndex)` 必须放在 `map.remove(val)` 前面。
- 也可以用更稳的分支写法：当 `deleteIndex != lastIndex` 时才交换和更新尾元素下标，最后统一删除尾部和 map 记录。