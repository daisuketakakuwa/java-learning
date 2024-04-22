# Learn kotlin....

## How to run on local

```
gradlew bootRun
```

# 総括(Java とちがい)

- new 使わない
- NPE 対策（基本 Non-nullable）
- Stream 化せずに filter,map らへんが使える
- Immutable という概念がもっと当たり前感
- 関数だけ定義できる -> **モジュールの概念が強くなるな**

```kt
interface Fixture{}

fun hello() {}

fun hoge() {}
```

- Pair という新しい概念

# Grammer

## 変数

### var, val, const

- var 　変更可能な(Mutable な)変数
- val 　変更不可能な(Immutable な)変数
- const つけるとコンパイル時に getter を生成しない = 効率的な定数となる。<br>
  　 val NAME = "d_takakuwa" -> class ファイルに getNAME が生成<br>
  　 const val NAME = "d_takakuwa" -> 直接 NAME 変数を参照しにいく。<br>
  **👉val で定数定義するときは、できるだけ const もつけよう。**

### 型つけない -> タイプ推論してくれる

```kt
val number: Int = 100 // ok
val number2 = 123 // Int型として推論される
```

### 基本 null が入ると NG、Nullable な変数は「?」をつけること

```kt
val name: String = null  // Compile Error
val name: String? = null // OK
```

### テンプレートリテラル

```kt
println("$a + $b = ${add(a, b)}")
```

## 配列, リスト, セット, マップ, Pair

配列

- **基本リスト使う。**

```kt
// arrayOf -> 個数が固定の配列を生成してくれる
var nums: Array<Int> = arrayOf(1, 2, 3)
var cols = arrayOf("Red", "Green", "Blue")

// 要素の更新は可能
nums[1] = 333

// Array<Int> は、Int オブジェクトの配列 Int[]
var nums1: Array<Int> = arrayOf(4, 5, 6)
// IntArray は、プリミティブ型 intの配列 int[]
var nums2: IntArray = arrayOf(7, 8, 9)
// Int[] と int[] なので、エラーとなる。
arrayOf(*nums1, *nums2).forEach{ num -> println(num)}
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

Pair

```kt


```

### コレクション関数

map, filter, forEach, reduce, any/all/none, sort 系, flatten/flatMap, groupBy, fold

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

Mutable 　 -> sort, sortBy<br>
Immutable -> sorted, sortedBy

リストの sort

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

Map の sort

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

flatten

```kt
val days = listOf("MON","TUE","WED","THU","FRI","SAT","SUN")
val months = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")

val allLists: List<List<String>> = listOf(days, months)
allLists.forEach{ println(it) } // [MON,--,SUN] と [Jan,--,Dec] で表示
// flattern -> nestしてるListを平坦にする
allLists.flatten().forEach{ println(it) } // MON,--,SUN,Jan,--,Dec で表示
```

flatMap

- **1 つの入力に対して、N 個の結果が返ってくるような繰り返し処理を行う**場合
- 複数の検索文字列で API を複数回コール -> 結果を１つのリストにまとめたい場合

```kt
// API Mock
fun fetchData(id: String): List<String> {
    val dataMap = mapOf(
        "1" to listOf("data1", "data2", "data3"),
        "2" to listOf("data4", "data5", "data6"),
        "3" to listOf("data7", "data8", "data9")
    )
    return dataMap.getOrElse(id) { emptyList() }
}

// Without flatMap
fun main() {
    val ids = listOf("1", "2", "3")
    val allResults = mutableListOf<String>()

    for (id in ids) {
        // 毎回 fetchしてリストに格納する
        allResults.addAll(fetchData(id))
    }
    println(allResults)
}

// With flatMap ->
fun main() {
    val ids = listOf("1", "2", "3")

    // 1渡して、N返ってきたやつを、flatにして、Listで返す
    val allFetchResults = ids.flatMap{ fetchData(it) }

    println(allFetchResults)
}

```

groupBy

fold

## レンジ

```kt
// 1. in関数でチェック
val monthRange = (1..12)
println(1 in monthRange) // true
println(0 !in monthRange) // true

// 2. rangeオブジェクト.map() で連番を生成
val counts = (1..100).map{ i -> "No. ${i}" }
counts.forEach{ println(it) }
```

## 構文(if, for, forEach, forEachIndexed, while, when, ラベル@)

## 関数

### JS みたいな省略記法

```kt
// 引数に型を指定しないとコンパイルエラーになる
fun uppercaseStr(value: String) = value.toUpperCase()
fun uppercaseAll(values: List<String>) = values.map { it.toUpperCase() }

fun main() {
   val names = mutableListOf("takakuwa", "john", "carmelo");
   val uppercasedNames = uppercaseAll(names);
   uppercasedNames.forEach{ n -> println(n) }
}
```

### Unit 関数

### Nothing 型関数

### 可変引数

### 無名関数 と ラムダ式

### インライン関数

### 拡張関数

### 中間記法関数(infix)

### Null 許容の?

### 関数参照の::

### スコープ関数

## クラス

## オブジェクト

### companion object？

## Stream

# 勉強フロー

- Controller - Validation - Service - Repository
- CRUD API をつくる<br>
  ・R: fetchPolicies -> map 使って変換, filter 使って抽出<br>
  ・C: ID 自動生成 -> Transaction<br>
  ・<br>
- Filter をつくる
- JWT 認証をつくる
- セッションを作る
- ModelMapper で DTO ⇔ Entity の変換を行う。

## Java からあるもの

- クラスの書き方(コンストラクタ,フィールド,メソッド)
- Stream 構文(map, filter) と ラムダ構文
- if 文
- for 文

## Kotlin からあるもの

-

## Gradle

### build.gradle の書き方
