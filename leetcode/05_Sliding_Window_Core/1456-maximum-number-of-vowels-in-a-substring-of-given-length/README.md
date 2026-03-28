# 1456. Maximun Number of Vowels in a substing of given length

## 我的思考
定长滑动窗口

## 卡点
1. 注意基础语法， set.put() 和 set.contains()

## 关键点
1. 可以直接使用helper函数
```java
    private boolean isVowel(char c) {
        return c == 'a' ||
               c == 'e' ||
               c == 'i' ||
               c == 'o' ||
               c == 'u';
    }
```
2. 也可以直接用String.indexOf(int ch)方法，返回字符在字符串中第一次出现的下标，找不到返回-1
即: "aeiou".indexOf(c) >= 0;