/**
1. 按 start 升序排序
2. 维护当前合并区间 [currStart, currEnd]
3. 遍历下一个区间 [nextStart, nextEnd]
   - 如果 currEnd >= nextStart，说明重叠，currEnd = max(currEnd, nextEnd)
   - 否则当前区间结束，加入 ans，开启新区间
4. 把最后一个 current 加入 ans
*/