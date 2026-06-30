297. 28min AC。层序序列化：非空节点写 val，空节点写 #；反序列化时队列只存真实节点，每弹出一个节点就依次读取两个 token 作为 left/right。主要耗时在手写 getNum 解析，实际面试可用 split 降低 parser bug 面。
```java
String[] tokens = data.split("/“);
```