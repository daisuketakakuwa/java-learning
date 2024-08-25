## SpringBootを用いた例外ハンドリング実装

|やりたいこと|使うもの|
|----|----|
|1Controller内での共通例外処理の定義|`@ExceptionHandler`|
|全Controllerに対する共通処理の適用|`@ControllerAdvice`|
|既に用意されているExceptionHandlerの利用|[ResponseEntityExceptionHandlerクラス](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.java#L642)|
|独自例外クラスに対する例外処理の定義|[ResponseEntityExceptionHandlerクラス](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.java#L642)の`handleExceptionInternal`関数をOverrideする|

### 1. Controller単体に例外ハンドラを定義する
- MyController内で発生した例外を**try-catch文を使わずに**ハンドリングするには、同じControllerクラス内に`@ExceptionHandler`を付与した関数にて処理を定義する。
```kt
@RestController
public class MyController {

    @GetMapping("/test")
    public String test() {
        throw new IllegalArgumentException("Illegal Argument!");
    }

    // MyControllerクラス内でIllegalArgumentExceptionが発生したときに実行。
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "Handled IllegalArgumentException: " + ex.getMessage();
    }
}
```

### 2. 例外ハンドラ定義を共通化する。
- 特定のControllerクラスだけに定義したいなら1のやり方でOK。
- しかし大半の例外はどのControllerでも同じような例外処理を定義することになるので、共通化したい。
    - 例外処理(`@ExceptionHandler`)を定義したクラスに`@ControllerAdvice`をつける -> 全Controllerクラスに適用される。
```kt
// このクラス内で定義されている例外処理(`@ExceptionHandler`)を共通化したい
//   → @ControllerAdviceを付与する。
@ControllerAdvice
class ControllerExceptionAdvice {

    private val logger: Logger = LoggerFactory.getLogger(ControllerExceptionAdvice::class.java)

    // RuntimeExceptionが発生したエラーログ出して、500エラーで返す。
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException, request: WebRequest): ResponseEntity<String> {
        logger.error("RuntimeException occurred: ${ex.message}", ex)
        return ResponseEntity("Internal Server Error: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
```

### 3. SpringBootで用意されている例外処理セットを使う。
- 1,2だと、各例外(XxxxxException)に対して`@ExceptionHandler`が付与された関数を１つ１つ自分で定義する必要がある。
- その手間を減らすために、SpringBootでは既に代表的な例外に対するハンドリング処理を定義してくれているクラス([ResponseEntityExceptionHandlerクラス](https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.java#L642))がある。
    - エラー詳細を本体に含む ResponseEntity を返すことで、Spring MVC で発生したすべての例外を処理する @ExceptionHandler メソッドを持つクラス。
    - アプリケーションでのグローバルな例外処理のための **@ControllerAdvice の基本クラスとして便利**です。
- ↓このように代表的な例外は一通りcatchされて、ResponseEntityに詰め直されてレスポンスとして返却される。
```kt
	/**
	 * Handle all exceptions raised within Spring MVC handling of the request.
	 * @param ex the exception to handle
	 * @param request the current request
	 */
	@ExceptionHandler({
			HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeNotSupportedException.class,
			HttpMediaTypeNotAcceptableException.class,
			MissingPathVariableException.class,
			MissingServletRequestParameterException.class,
			MissingServletRequestPartException.class,
			ServletRequestBindingException.class,
			MethodArgumentNotValidException.class,
			HandlerMethodValidationException.class,
			NoHandlerFoundException.class,
			NoResourceFoundException.class,
			AsyncRequestTimeoutException.class,
			ErrorResponseException.class,
			MaxUploadSizeExceededException.class,
			ConversionNotSupportedException.class,
			TypeMismatchException.class,
			HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class,
			MethodValidationException.class,
			BindException.class,
			AsyncRequestNotUsableException.class
		})
	@Nullable
	public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) throws Exception {
        ...以下省略...
```

とりあえず↑だけ適用したいなら、↓のように定義するだけでOK。
```kt
@ControllerAdvice(annotations = [RestController::class])
class ExceptionControllerAdvice: ResponseEntityExceptionHandler() {
```

### 4. SpringBoot以外にもあるあるな例外に対するハンドリング定義
- 3であげたものは基本的にAPIのIF絡み。
- 他にも↓に対して@ExceptionHandlerを定義していく。
    - DB操作エラーで発生する`PersistenceException`
    - 業務ロジック絡み → 5番へ


### 5. 独自例外に対しての例外処理を定義する。
- **このREADMEでの本題はここ。**
- 一般的な例外パターンは3で問題なし。本題は`業務ロジック固有の例外パターン`の定義。
- CustomBusinessExceptionクラスを定義＆ハンドリングするとしたら、以下がポイントになる。
    - 処理フローは「例外catch」→「例外処理」→「ResponseEntity詰めて返す」
        - 「例外catch」→ @ExceptionHandler定義
        - 「例外処理」→ **-----------ここが大事-----------**
        - 「ResponseEntity詰めて返す」→ ResponseEntityExceptionHandlerクラスの`handleExceptionInternal`関数

```kt
// 想定内の例外は"想定内"なのでINFO/WARNログ出すが、ものによってERRORログ出す
// 想定外の例外は全てERRORログを出す。
```

### 6. 独自例外クラスの作り方

- 想定内例外クラスの親玉として`BusinessException`クラスを作成。
    - ここから"想定内"な例外を子クラスとして定義していく。
        - ValidationException（バリデーション系）
        - ResourceNotFoundException（リソースがない系）
        - NoPermissionException（アクセス権限がない系）
