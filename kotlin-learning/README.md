# Learn kotlin....

## How to run on local
```
gradlew bootRun
```

# 総括(Javaとちがい)
- new使わない
- NPE対策（基本Non-nullable）
- Stream化せずにfilter,mapらへんが使える
- Immutableという概念がもっと当たり前感

# Grammer
## 変数

### var, val, const
- var　変更可能な(Mutableな)変数
- val　変更不可能な(Immutableな)変数
- const つけるとコンパイル時にgetterを生成しない = 効率的な定数となる。<br>
　val NAME = "d_takakuwa" -> classファイルに getNAME が生成<br>
　const val NAME = "d_takakuwa" -> 直接 NAME変数を参照しにいく。<br>
**👉valで定数定義するときは、できるだけconstもつけよう。**

### 型つけない -> タイプ推論してくれる
```kt
val number: Int = 100 // ok
val number2 = 123 // Int型として推論される
```

### 基本nullが入るとNG、Nullableな変数は「?」をつけること
```kt
val name: String = null  // Compile Error
val name: String? = null // OK
```

### テンプレートリテラル
```kt
println("$a + $b = ${add(a, b)}")
```

## 配列, リスト, セット, マップ
配列
```kt
// arrayOf -> 個数が固定の配列を生成してくれる
var nums = arrayOf(1, 2, 3)
var cols = arrayOf("Red", "Green", "Blue")

// 要素の更新は可能
nums[1] = 333
```
リスト
```kt
// listOf -> 不変(Immutable)なリスト ※JavaだとList.of
var colors = listOf("Red", "Green", "Blue")
// mutableListOf -> 可変(Mutable)なリスト
var colors2 = mutableListOf("Red", "Green", "Blue")

colors1.get(1) // 取得1
colors1[1]     // 取得2

// immutableであれば add, remove で追加削除が可能
colors.add(4)       // 追加
colors.remove(0)    // 削除
colors[1] = "White" // 更新
```

マップ
```kt
// mapOf -> 不変(Immutable)なマップ ※JavaだとMap.of
var map = mapOf("Red" to "#f00", "Green" to "#0f0", "Blue" to "#00f")
// mutableMapOf -> 可変(Mutable)なマップ
var map2 = mutableMapOf("Red" to "#f00", "Green" to "#0f0", "Blue" to "#00f")
map2["Red"]     // 取得1
map2.get("Red") // 取得2
map2.put("Yellow", "#ff0") // 追加
map2.remove("Yellow")      // 削除

for ((key, value) in map2) {
    println("$key = $value")
}
```

### コレクション関数
map, filter, forEach, reduce, any/all/none, sort系, groupBy, fold

filter -> 抽出
```kt
var names = mutableListOf("takakuwa", "makito", "ryo")
var filtered1 = names.filter{ n -> n.contains("o") } // Lambda
var filtered2 = filtered1.filter{ it.contains("m") } // Lambda with it(暗黙param)
```

map -> 変換
```kt
var names = mutableMapOf(1 to "takakuwa", 2 to "makito", 3 to "ryo")

// マップの場合は 引数２つで itは使えない。
var mapped1 = names.map { entry -> entry.key to entry.value.toUpperCase()}
var mapped2 = mapped1.map { (key,value) -> key to "${value}-san" }

for((key, value) in mapped2) {
    println("$key = $value")
}
```

forEach -> 副作用
```kt
var ids = mutableListOf(1,2,3,4,5)
ids.forEach{ id -> println(id) } // Lambda
ids.forEach{ println(it) }       // Lambda with it
```

reduce -> 合計
```kt
var ids = mutableListOf(1,2,3,4,5)
val sum = ids.reduce { acc, number -> acc + number }
```

any, all, none -> 判定
```kt
var ids = mutableListOf(1,2,3,4,5)
ids.any { it % 2 == 0 }  // true
ids.all { it % 2 == 0 }  // false
ids.none { it % 6 == 0 } // true
```

Mutable　 -> sort, sortBy<br>
Immutable -> sorted, sortedBy

リストのsort
```kt
data class Person(val name: String, val age: Int)

// Mutableなリストのソート sort, sortBy(指定)
// １．数字
var mIds = mutableListOf(4,1,2,3,5)
mIds.sort() // mutableなので元をソート
println(mIds)
// ２．インスタンス
val mPeople = mutableListOf(Person("Alice", 30),Person("Bob", 25),Person("Charlie", 35))
mPeople.sortBy{ it.age } // mutableなので元をソート Comparableオブジェクト
println(mPeople)

// Immutableなリストのソート sorted, sortedBy(指定)
// １．数字
val imIds = listOf(4,1,2,3,5)
val sortedIds = imIds.sorted().reversed() // immutableなので新しいものを返す
println(sortedIds)
// ２．インスタンス
val imPeople = listOf(Person("Alice", 30),Person("Bob", 25),Person("Charlie", 35))
val sortedImPeople = imPeople.sortedBy{ it.age } //  Comparableオブジェクト
println(sortedImPeople)
```

Mapのsort
```kt
// Mutableなマップのソート 
val peopleMap = mutableMapOf(3 to "takakuwa", 1 to "akito", 2 to "zhang")
// １．Keyでソート
val sortedByKeys = peopleMap.toSortedMap()
println(sortedByKeys)
    
// ２．Sortキーをカスタマイズ toListでimmutableなListにしてから変換
val sortedByValueDesc = peopleMap.toList().sortedBy { (key, value) -> value}.toMap()
println(sortedByValueDesc) 
```

## レンジ



## 構文(if, for, forEach, forEachIndexed, while, when, ラベル@)

## 関数 
### Unit関数
### Nothing型関数
### 可変引数
### 無名関数 と ラムダ式
### インライン関数
### 拡張関数
### 中間記法関数(infix)
### Null許容の?
### 関数参照の::
### スコープ関数

## クラス

## オブジェクト

### companion object？

## Stream




# 勉強フロー
- Controller - Validation - Service - Repository
- CRUD APIをつくる<br>
・R: fetchPolicies -> map使って変換, filter使って抽出<br>
・C: ID自動生成 -> Transaction<br>
・<br>
- Filterをつくる
- JWT認証をつくる
- セッションを作る
- ModelMapperで DTO ⇔ Entity の変換を行う。


## Java からあるもの

- クラスの書き方(コンストラクタ,フィールド,メソッド)
- Stream 構文(map, filter) と ラムダ構文
- if 文
- for 文

## Kotlin からあるもの

-

## Gradle

### build.gradle の書き方
