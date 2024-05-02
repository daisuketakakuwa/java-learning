# Learn kotlin....

## How to run on local

```
// DB起動
docker compose up -d
// ログイン
psql -p 5435 -U postgres -d kotlinDb

// アプリ起動
gradlew bootRun
```

# MyBatis
- [MyBatis-Spring-Boot-Starter](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)を利用する。
- `DataSource`,`SqlSessionFactory`周りのboilertemplateを書かずに済む！
- 用意するのは Mapper(XML), Mapper(Java/IF) の２つ
- Mapper(XML)は `src/main/resouces`配下に定義する。
- 通常なら`mybatis-config.xml`にて、MapperXMLの場所を１つ１つ定義する必要があるが、mybatis-spring-boot-starterを使えば`@MapperScan`で**MapperXMLが配置されているクラスパス**(**src/main/resouces/XXX**)を指定してスキャンすればOK👍
- 🔴XMLファイルでの実装 と 🔵アノテーションでの実装 の２つある。

### MapperXMLを自動スキャン

MapperIFをDIコンテナに登録する by `@MapperScan`<br>
→ ✅**MyBatisが自動的にIFの実装を提供してくれる。**
```kt
@MapperScan("jp.ats.kotlinlearning.repository")
@SpringBootApplication
class KotlinLearningApplication
```

### マッピングするクラスは空コンストラクタを用意すること -> [参考](https://qiita.com/5zm/items/0864d6641c65f976d415#17-%E3%83%9E%E3%83%83%E3%83%94%E3%83%B3%E3%82%B0%E3%81%99%E3%82%8B%E3%82%AF%E3%83%A9%E3%82%B9%E3%81%AB%E3%81%AF%E7%A9%BA%E3%81%AE%E3%82%B3%E3%83%B3%E3%82%B9%E3%83%88%E3%83%A9%E3%82%AF%E3%82%BF%E3%81%8C%E5%AD%98%E5%9C%A8%E3%81%99%E3%82%8B%E3%81%93%E3%81%A8)
```kt
data class EventWithParticipants(
    val id: Long, // BigSerialだからLong
    val eventName: String,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val organizer: Organizer?,
    val participants: List<Participant>
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor() : this(
        1L,
        "",
        null,null,null,
        // mutableなListでないとエラーとなる
        mutableListOf()
    )
}
```

# DbSetup
https://dbsetup.ninja-squad.com/approach.html


# Grammer of Kotlin

## 総括(Java とちがい)

- new 使わない
- `public/private`に加えて `val/var` 
- NPE 対策（基本 Non-nullable）
- Stream 化せずに filter,map らへんが使える
- Immutable という概念がもっと当たり前感
- 関数だけ定義できる -> **モジュールの概念が強くなるな**

```kt
interface Fixture{}

fun hello() {}

fun hoge() {}
```




## Null対策
- safe call(`?.`)
- `!!` operator
- Elvis operator(`?:`)

### Safe call
- Nullだったら`?.xxx`の`xxx`部分のフィールド参照/関数実行はせずにnullを返す。
- 名前の通り**Nullだったら参照せずにNullを返すだけ**なので、ヌルポが発生しない👍

```kt
fun main() {
    val nullableName: String? = null
    val result = nullableName?.length
    println(result) //  null
}
```

### !! operator
- 「Nullかも判定されてるけど、Nullじゃないから絶対`xxx`部分を参照してね！！！」という指示。
- なので`!!`**をつけて**`xxx`**がNullだったらヌルポ発生して終わる。**

```kt
fun main() {
    val nullableName: String? = null
    val result = nullableName!!.length // NullPointerException
    println(result)
}
```

### エルビス演算子(?:) - A || B みたいなことができる👍
```kt
fun main() {
    val nullableName: String? = null
    val result = nullableName ?: "unknown"
    println(result) // unknown
}
```

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

### テンプレートリテラル

```kt
println("$a + $b = ${add(a, b)}")
```

## 配列, リスト, セット, マップ, Pair

### 配列 ※配列あるけど、基本リスト使う

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

### リスト
- mutableListOfでもarrayListOfでも動作あまり変わらない。
- 

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

### マップ

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

### Pair

```kt


```

### Triple

```kt


```


## コレクション関数

map, filter, forEach, reduce/fold, any/all/none, sort 系, flatten/flatMap, groupBy

### filter -> 抽出

```kt
var names = mutableListOf("takakuwa", "makito", "ryo")
var filtered1 = names.filter{ n -> n.contains("o") } // Lambda
var filtered2 = filtered1.filter{ it.contains("m") } // Lambda with it(暗黙param)
```

### map -> 変換

```kt
var names = mutableMapOf(1 to "takakuwa", 2 to "makito", 3 to "ryo")

// マップの場合は 引数２つで itは使えない。
var mapped1 = names.map { entry -> entry.key to entry.value.toUpperCase()}
var mapped2 = mapped1.map { (key,value) -> key to "${value}-san" }

for((key, value) in mapped2) {
    println("$key = $value")
}
```

### forEach, forEachIndexed -> 副作用

```kt
fun main() {
  var ids = mutableListOf(1,2,3,4,5)
  ids.forEach{ id -> println(id) } // Lambda
  ids.forEach{ println(it) }       // Lambda with it
}

data class User(val name: String, val age: Int)

fun main() {
  val names = listOf("taka", "maki", "ryo")
  names.forEachIndexed{ index, elem -> println("$index: $elem")}
}
```

### reduce -> 合計 ※初期値なし

```kt
fun main() {
  var ids = mutableListOf(1,2,3,4,5)
  val sum = ids.reduce { acc, number -> acc + number }
  println(sum)
}
```

### fold -> 合計 ※初期値あり

```kt
fun main() {
    var ids = mutableListOf(1,2,3,4,5)
    // 第1引数: 初期値, 第2引数Lambda -> 波括弧{}でくくること!
  	val sum = ids.fold(100) { acc, number -> acc + number }
    println(sum)
}
```


### any, all, none -> 判定

```kt
var ids = mutableListOf(1,2,3,4,5)
ids.any { it % 2 == 0 }  // true
ids.all { it % 2 == 0 }  // false
ids.none { it % 6 == 0 } // true
```

### リストの sort

- Mutable 　 -> sort, sortBy<br>
- Immutable -> sorted, sortedBy


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

### Map の sort

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

### リストのflatten

```kt
val days = listOf("MON","TUE","WED","THU","FRI","SAT","SUN")
val months = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")

val allLists: List<List<String>> = listOf(days, months)
allLists.forEach{ println(it) } // [MON,--,SUN] と [Jan,--,Dec] で表示
// flattern -> nestしてるListを平坦にする
allLists.flatten().forEach{ println(it) } // MON,--,SUN,Jan,--,Dec で表示
```

### flatMap

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

### groupBy - SQLのGROPUBY的な -> 戻り値はMap

```kt
data class Policy(val policyNo: String, val type: Int)

fun main() {
    var policies = listOf(
    	Policy("10001", 1),
        Policy("10002", 2),
        Policy("10003", 1),
        Policy("10004", 3),
        Policy("10005", 2),
        Policy("10006", 3),
        Policy("10007", 1),
        Policy("10008", 1),
    )
    var groups = policies.groupBy{ it.type }
    // グループ化の単位がMapのkeyとなる
    for((key, policies) in groups) {
        println("type: $key, count: ${policies.size}")
        policies.forEach{ println("    -> $it") }
        println("")
    }
}

// type: 1, count: 4
//     -> Policy(policyNo=10001, type=1)
//     -> Policy(policyNo=10003, type=1)
//     -> Policy(policyNo=10007, type=1)
//     -> Policy(policyNo=10008, type=1)
//
// type: 2, count: 2
//     -> Policy(policyNo=10002, type=2)
//     -> Policy(policyNo=10005, type=2)
//
// type: 3, count: 2
//     -> Policy(policyNo=10004, type=3)
//     -> Policy(policyNo=10006, type=3)
```


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

<br>

## 構文(if, for, while, when)
```kt
data class User(val name: String, val age: Int)

fun main() {
    // if文
    val name = "taka"
    if (name == "taka") {
    	println("yes")
    } else {
        println("no")
    }
    
    // 三項演算子
    val result = if (name == "taka") "yes" else "no"
    println("result is $result")
    
    //  for文 - 要素のみ
    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }
    // for文 - index番号
    for (index in items.indices) {
        println("$index: ${items.get(index)}")
    }
    
    // when - switch文的な
    val targetName = "hoge"
    val whenResult = when(targetName) {
        "apple" -> "APPLE"
        "banana" -> "BANANA"
        else -> "UNKNOWN"
    }
    println(whenResult)
    
    // when - switch(true)的なやつ
    val user = User("taka", 10)
    val authResult = when {
        user.name == "unknon" -> "user is Unknown"
        user.age < 18 -> "user is under 18."
        else -> "user is valid"
    }
    println(authResult)
}
```

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

<br>

### スコープ関数
- 【オブジェクト】に対する操作
- let, run, with, apply, also
- `String?` のような Nullable なオブジェクトであれば、`safe call(?.)` か `non-null aserted call(!!.)` で関数を呼び出す（**スコープ関数に限った話ではないが 👍**）

<br>

#### let (オブジェクトを "INPUT" に副作用的な処理をする)

- [公式](https://kotlinlang.org/docs/scope-functions.html#function-selection)には "Executing a lambda on **NON-NULLABLE** objects." とあるが、他のスコープ関数も null ではないオブジェクトに対して実行されるわけだから、今のところこの説明はしっくりこない(´\_ゝ｀)
- 副作用的な処理としては<br>
  　 A) オブジェクトより値取得<br>
  　 B) オブジェクトを別のオブジェクトへ変換する<br>
  といった　**オブジェクトを「INPUT」として HOGEHOGE する**使い方
- 引数 it を受け取り、lambda 実行結果を返す。

```kt
data class SendEmailRequest(val to: MutableList<String>, val cc: MutableList<String>, val bcc: MutableList<String>)
data class Policy(val policyNo: String, val type: String, val holderName: String)

fun main() {
  val req = SendEmailRequest(
    to = mutableListOf("to1", "to2", "to3"),
    cc = mutableListOf("cc1", "cc2", "cc3"),
    bcc = mutableListOf("bcc1", "bcc2", "bcc3")
  )

  // RequestオブジェクトをINPUTに、副作用(新たなオブジェクト生成処理)を実行。
  val allDestAddresses = req.let{ listOf(it.to, it.cc, it.bcc).flatten() }
  println(allDestAddresses) // [to1, to2, to3, cc1, cc2, cc3, bcc1, bcc2, bcc3]

  // PolicyオブジェクトをINPUTに、副作用(値抽出)を実行。
  val policy = Policy("12345", "保険A", "tanaka")
  val policyNo = policy.let{ p ->
    println("---- fetch policyInfo $p")
    p.policyNo
  }
  println("PolicyNo is $policyNo")
}
```

```kt
// 例外を取得＆let内でログレベルに応じてログ出力

```

<br>

#### also (let の副作用＋戻り値はもとのオブジェクトに手を加えたものにしたい場合)

- **ADDITIONAL effects on objects**.
- **ラムダ内で【元の】オブジェクト自体を変更しながら、その変更後の状態を確認したい場合に適している。**
- 引数 it を受け取り、this を返す。

```kt
val numbers = mutableListOf(1, 2, 3, 4, 5).also {
    println("Original list: $it")
    it.add(6)
}
println("Updated list: $numbers")
```

<br>

#### apply (オブジェクトを生成＆オブジェクトに対する操作/構築 -> this が戻り値)

- Object configuration
- **Operations on the members of the object👍**
- 引数 this を受け取り、this を返す。

```kt
// ValidationErrorがあれば、例外をなげる
data class SearchPolicyRequest(val id: String, val email: String)
data class ValidationPattern(val regex: String, val errorCode: String)

fun main() {
  val req = SearchPolicyRequest(id = "1234567890", email = "hoge@gmail.com")

  // Listオブジェクトを構築(Errorがあれば値追加していく)
  mutableListOf<String>().apply {
    // String(errorCode)も立派なオブジェクト、あれば副作用(追加処理)を行う。
    getErrorCodeIfInvalid("id", req.id)?.let{ errorCode -> add(errorCode)}
    // String(errorCode)も立派なオブジェクト、あれば副作用(追加処理)を行う。
    getErrorCodeIfInvalid("email", req.email)?.let{ errorCode -> add(errorCode)}
  }.throwIfNotEmpty("Validation error")

}

// OK -> null返す　NG -> errorCode返す
fun getErrorCodeIfInvalid(key: String, value: String): String? {
  val validationPatternMap = mutableMapOf<String, ValidationPattern>(
    "id" to ValidationPattern(regex = "[a-zA-Z0-9]{10}", errorCode = "INVALID_ID"),
    "email" to ValidationPattern(regex = ".+@.+", errorCode = "INVALID_EMAIL"),
  )
  val pattern = validationPatternMap.get(key)
  return if (pattern != null && Regex(pattern.regex).matches(value)) null else pattern?.errorCode
}

// こうやることで、Collection自体にメソッドを追加することができる👍
fun Collection<String>.throwIfNotEmpty(message: String) {
  if (isNotEmpty()) {
    throw IllegalStateException(message)
  }
}
```

<br>

#### with（**オブジェクトに組み込まれる拡張関数ではない**）

- "recommend using with for calling functions on the context object **when you don't need to use the returned result."**
- **run と同じ役割を担うが、with は戻り値を使わないやり方で 👍**
- 拡張関数ではないので **switch 文的な使い方**
- 引数 this を受け取り、it を返す。

```kt
class Person(var name: String, var age: Int)

fun main() {
    val person = Person("Alice", 30)
    // ✕ NOT recommended using returned result
    val modifiedPerson = with(person) {
        name = "Bob"  // nameを変更
        age += 5      // ageに5を加える
        this          // 変更後のpersonオブジェクトを返す
    }

    println("Modified Person: ${modifiedPerson.name}, Age: ${modifiedPerson.age}")
}

fun main() {
  val numbers = mutableListOf("one", "two", "three")
  // 〇 Recommended using returned value
  with(numbers) {
      println("'with' is called with argument $this")
      println("It contains $size elements")
  }
}
```

<br>

#### run（**with の拡張関数バージョン**）

- `run` is useful when your lambda both initializes objects and computes the return value.
- 以下の流れでよく使う。<br>
  　 1. オブジェクトを初期化<br>
  　 2. オブジェクトを INPUT に lambda 実行<br>
  　 3. 2 の実行結果を戻り値として受け取る。<br>
  **API コール -> レスポンス取得 のような流れ**
- 引数 this を受け取り、it を返す。

```kt
fun main() {
  val service = MultiportService("https://example.kotlinlang.org", 80)

  val result = service.run {
      port = 8080
      query(prepareRequest() + " to port $port")
  }

}
```

<br>

### 無名クラス, 無名関数, Lambda, SAM 変換

#### 無名クラス

- `class`キーワードを使わずにクラス定義されたやつ＝無名クラス
- 無名クラスは、通常のクラスとは異なり、**定義してすぐその場でインスタンス化**される。
- 元となる Interface/Class が必要。

＝ Java ＝

```java
// Bookクラスを元に新たな無名クラス定義＆インスタンス化
new Book("Design Patterns") {
    @Override
    public String description() {
        return "Famous GoF book.";
    }
}

// Interfaceを元に新たな無名クラス(ComparaterIF実装クラス)定義＆インスタンス化
Comparator<Integer> comparator = new Comparator<Integer>() {
    @Override
    public int compare(Integer a, Integer b) {
        return a.compareTo(b);
    }
};
List<Integer> numbers = new ArrayList<>(Arrays.asList(4, 3, 5));
numbers.sort(comparator);

// ↓Comparatorオブジェクト を Lambda で表現
numbers.sort((a, b) -> a.compareTo(b));
```

＝ Kotlin ＝

```kt
// 丁寧にクラス定義してあげるパターン
class IntComparator : Comparator<Int> {
    override fun compare(a: Int, b: Int): Int {
        return a.compareTo(b)
    }
}
fun main() {
    val numbers = listOf(3, 1, 4, 1, 5, 9)
    numbers.sortedWith(IntComparator()).also { println(it) }
}

// 無名クラスを定義 ＆ Object構文(object:)でインスタンス化
fun main() {
    val comparator = object : Comparator<Int> {
        override fun compare(a: Int, b: Int): Int {
            return a.compareTo(b)
        }
	}
    val numbers = listOf(3, 1, 4, 1, 5, 9)
    numbers.sortedWith(comparator).also { println(it) }
    // 直接引数内で定義してもOK
    numbers.sortedWith(object : Comparator<Int> {
        override fun compare(a: Int, b: Int): Int {
            return a.compareTo(b)
        }
	 }).also{ println(it) }
}

// SAM変換 by 無名関数
fun main() {
    val numbers = listOf(3, 1, 4, 1, 5, 9)
    numbers.sortedWith(fun(a: Int, b: Int): Int {
        return a.compareTo(b)
    }).also { println(it) }
}

// SAM変換 by Lambda -> ★Lambdaのときは波括弧 { } を使う。
fun main() {
    val numbers = listOf(3, 1, 4, 1, 5, 9)
    numbers.sortedWith{ a, b -> a.compareTo(b) }.also { println(it) }
}
```

<br><br>

### Lambda
- 引数にLambdaを定義するときは、波括弧`{}`でくくる。
- Lambda以外の引数もある場合は `()` と `{}` で分ける。

```kt
private fun introduce(name: String, age: String, doit: (String,String) -> Unit ) {
    doit(name, age)
}


fun main() {
    // (通常引数) {Lambda引数}
    introduce("Takakuwa", "99") { name, age -> println("I'm $name. My age is $age ")}
}
```

<br>

### 戻り値型が定義されてない関数 -> Unit関数として扱われる

```kt
fun printMessage(msg: String) {      // : Unit が不要
    println(msg)
}
```

<br>

### 常に例外を返す関数 -> Nothing型関数
- 戻り値を返すことができないから「Nothing」型

```kt
fun raiseError(msg: String): Nothing {
    throw MyException(msg)
}
```

<br>

### 拡張関数
- 事前に定義されているクラスに対して、もとのクラス定義をいじることなく関数を追加できる👍

```kt
fun Collection<String>.throwIfNotEmpty(message: String) {
  if (isNotEmpty()) {
    throw IllegalStateException(message)
  }
}
```
<br>


### インライン/inline 関数
- Inline functions in Kotlin work by inlining the function code at the **call site(呼び出し元)**.
- **コンパイル時に呼び出している関数を呼び出し元に差し込む。**<br>
  -> 〇パフォーマンス向上 by 関数呼び出しのオーバヘッド の削減<br>
  -> △ コード量が増える"可能性"がある。
- 🔴**関数をパラメータとして受け取る関数**について使うべき!!!
```kt
inline fun add(x: Int, y: Int): Int {
    return x + y
}
// コンパイル前
val result = add(3, 4)
// コンパイル後
val result = 3 + 4
```
```kt
// 関数がパラメータとして定義されてるもの
private inline fun processData(value: String, processor: (String) -> String): String {
  return processor(value)
}

fun main() {
    // コンパイル前(インライン化される前)
    val result = processData("hello") { value -> value.toUpperCase() }
    // コンパイル後(インライン化された後)
    val resultAfterInlining = { value: String -> value.toUpperCase() }("hello")
  
    println(result)
}
```

### クロスライン/crossline 関数
- inline関数の引数としてのLambdaを「crossline」にするかどうか

```kt
private inline fun processStrs(strs: List<String>, crossinline processor: (String) -> String): List<String> {
  val wholeProcessor = {
    println("--- process started ---")
    val result = strs.map { processor(it) }
    println("--- process finished ---")
    result
  }

  return wholeProcessor()
}

fun main() {
  var names = listOf("taka", "maki", "ryo")
  var processedNames = processStrs(names) { name: String -> name.toUpperCase() }
  println(processedNames)

}
```

<br>

### 可変引数　vararg

```kt
fun foo(vararg args: String) {
    args.forEach {
        println(it)
    }
}

fun main() {
    foo("A", "B", "C")
}
```

<br>

## クラス
- var/valをインスタンス変数につけることで、getter/setterが自動生成される。<br>
　→ **var/valをつけないなら、自分でgetter/setterを定義してあげる必要があるってこと👍**
- プライマリコンストラクタで`val/var`**をつけると「インスタンス変数定義」「変数の初期化」をしてくれる**。
- 自分でインスタンス変数定義＆getter/setterを定義したい場合は、**プライマリコンストラクタは使わないこと**。
- ✅**プライマリコンストラクタ × `val` = インスタンス変数自動定義＆getter/setter自動生成**

```kt
// 1. 引数にvar/val定義なし
class Person (name: String, age: Int)
// ↓ と同義(コンパイル時に変換)
class Person {
  // インスタンス変数は定義されない
  // コンストラクタは生成される
  constructor(name: String, age: Int) {
    // インスタンス変数が定義されていないので何もおきない
  }
  // getter/setterが生成されない
}
```
```kt
// 2. 引数にvar定義あり ※valだとgetterのみ生成
class Person (var name: String, var age: Int)
// ↓ と同義(コンパイル時に変換)
class Person {
    // varなのでgetter/setter自動生成
    var name: String = ""
        get() = field
        set(value) { field = value }
    // varなのでgetter/setter自動生成
    var age: Int = 0
        get() = field
        set(value) { field = value }
    // プライマリコンストラクタで自動生成
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}
```
```kt
// 3. customでgetter/setter定義したい場合、
//    - プライマリコンストラクタは使わない
//      -> 自分で「変数定義」と「getter/setter」を手動定義したいから！
class Person {
    // 自分でインスタンス変数の初期値,getter,setterを定義する👍
    var name: String = "default"
    	get() = field.toUpperCase()
    	set(value) { field = value.toUpperCase() }
    var age: Int
    
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}
```

### セカンダリコンストラクタの省略記法

```kt
data class Person(val name: String? = null, val age: String? = null) {
    constructor() : this(null, null)
}
```

## 継承
- 継承させるには`open`キーワードが必要。
- クラス, 関数に加えてインスタンス変数 もoverride可能👍
- **抽象クラス/interfaceに含まれるメソッドやプロパティは暗黙的にopen**なので、`open`キーワード付与はしなくていい -> 拡張前提だもんね。

```kt
open class BaseApiRequest(
	val url: String,
    val method: String,
    val token: String,
)

// url, method, tokenは親クラスでインスタンス変数として定義済＆継承するので、valはつけない。
class PolicyApiRequest(
   	url: String,
    method: String,
    token: String,
    val policyNo: String,
    val policyHolder: String
): BaseApiRequest(url, method, token) {
    override fun toString(): String{
        return "url: $url\nmethod: $method\ntoken: $token\npolicyNo: $policyNo\npolicyHolder: $policyHolder"
    }
}


fun main() {   
	val policyApiReq = PolicyApiRequest(
    	url = "https://policy-api/policies",
        method = "GET",
        token = "AFJIDOJOIAJIDSA",
        policyNo = "90000001",
        policyHolder = "aaaaaa.bbbbbbb"
    )
    println(policyApiReq)
}
```

## Interface
- IFは「型」なので、インスタンス生成はできない。
- 🔴しかし、IF内にNestされた`enum`や`data class`は`IF.Xxxx`で参照可能であり、このIFに関連するものはNestする形で色々定義していくのが主流っぽい...
- ✅なので、コード読むときは「**型の役割を果たしているもの**」を中心に見るとよい👍
- IF = 「型」であり、IF実装クラス(インスタンス)が「状態」をもつ【**IFは状態をもたない**】
- "Interface cannot store states"
- Interface can contains declarations of<br>
  ・abstract properties<br>
  ・properties with accessor
  ・abstract methods<br>
  ・method implementations（振る舞いだけだから状態をもたない）<br>
  ※class<br>
  ※data class<br>
  ※enum<br>

```kt
interface HogeInterface {
    // IF内にあってもアプリ起動時にSingletonインスタンス生成される
    enum class FileType {
        JPEG
    }
    // IF内にNestして定義してるだけ。
    data class Person(val name: String, val age: Int)
}
```

<br>

## Operator overloading
- インスタンスA + インスタンスB のような演算を可能にする👍
```kt
data class Point(val point: Int) {
    // 関数名は 演算子ごとに調べる
    operator fun plus(other: Point): Point {
        return Point(this.point + other.point)
    }
}

fun main() {
	val p1 = Point(1)
    val p2 = Point(2)
    
    val p3 = p1 + p2
    println(p3)
}
```

https://kotlinlang.org/docs/operator-overloading.html

https://qiita.com/KokiEnomoto/items/2fedf864ff0710927b98

https://www.tohoho-web.com/ex/kotlin.html#operator

## Companion object（インスタンス化せずに呼べる）
- **Factoryメソッドにてよく使われるパターン**

```kt
// private -> 外からインスタンス化できない。
class Person private constructor(name: String, age: Int) {
    companion object {
        // Person.create(xxx)で インスタンス生成してもらう
        fun create(name: String, age: Int): Person {
            return Person(name, age)
        }
    }
}

fun main() {
    val p1 = Person.create("taka", 1)
    println(p1)
}
```

## invoke operator


## Enum

- アプリ起動時にSingletonインスタンスとして初期化される
- コンストラクタ引数だけで完結できるパターンもあるし、IF用意するもよし↓

```kt
// パターン1. コンストラクタ引数定義 ＆ 直接プロパティを参照
enum class FileType1(val contentType: String, val extension: String) {
    JPEG(contentType = "image/jpeg", extension = ".jpg"),
    PNG(contentType = "image/png", extension = ".png"),
    PDF(contentType = "application/pdf", extension = ".pdf")
}
fun main() {
    println(FileType1.JPEG) // JPEG
	println(FileType1.JPEG.contentType) // image/jpeg
}


// パターン2. IFを定義 ＆ 各enumで実装 & メソッド実行
enum class FileType2 {
    JPEG {
        override fun contentType() = "image/jpeg"
        override fun extension() = ".jpg"
    },
    PNG {
        override fun contentType() = "image/png"
        override fun extension() = ".png"
    },
    PDF {
        override fun contentType() = "application/pdf"
        override fun extension() = ".pdf"
    };

    abstract fun contentType(): String
    abstract fun extension(): String
}

// パターン2がメソッドなので、後から拡張しやすいくらい？
```

- companion objectを用いて、該当するenumがあれば返すようなfactory用メソッドを用意する👍

```kt
enum class FileType(val contentType: String, val extension: String) {
    JPEG(contentType = "image/jpeg", extension = ".jpg"),
    PNG(contentType = "image/png", extension = ".png"),
    PDF(contentType = "application/pdf", extension = ".pdf");
    
    companion object {
        // factoryメソッド
        fun findFileType(targetType: String): FileType? =
            when(targetType) {
                "JPEG" -> JPEG
                "PNG"  -> PNG
                "PDF"  -> PDF
                else   -> null
            }
    }
}

fun main() {   
    val targetFileType = FileType.findFileType("JPEG")
    println(targetFileType)
}
```

## object

- Loggerのような`public static`/Singletonインスタンスで定義したい処理や定数を objectで定義する。
- アプリ起動時ではなく、参照されたら遅延初期化でインスタンス生成される。

```kt
class MockLogger {
    fun debug(message: String) = println(message)
    fun warn(message: String) = println(message)
    fun info(message: String) = println(message)
    fun error(message: String) = println(message)
}

// 参照されたら遅延初期化でSingletonインスタンス生成
object Logger {
	private val logger = MockLogger() // 本当ならlog4j等で初期化
    
    fun debug(message: String) {
        logger.debug("DEBUG: $message")
    }
    fun warn(message: String) {
        logger.warn("WARN: $message")
    }
    fun info(message: String) {
        logger.info("INFO: $message")
    }
    fun error(message: String) {
        logger.error("ERROR: $message")
    }
}

fun main() {   
	Logger.debug("application started.")
}
```



## data class



## 便利な標準組み込み関数
- takeIf

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

## Gradle

### build.gradle の書き方
