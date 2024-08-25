package validator.customvalidator.custom_validator.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * 想定内の例外はこいつを継承して作成する。
 */
abstract class BusinessException(
    // ResponseEntity内のBodyに出力する。
    private val form: Map<String,Any> = emptyMap(),
    // message, causeは既に親(RuntimeException)で定義済なのでvalをつけない
    message: String? = null, // ハンドル関数内でログ出力する、ResponseEntity内には格納しない
    cause: Throwable? = null // はんｄ
): RuntimeException(message, cause) {

    /**
     * ValidationExceptionの場合はエラー情報をform項目に含める。
     * {
     *      "id": "ValidationException",
     *      "form": {
     *          "itemA": "20文字以内で入力して下さい",
     *          "itemB": "0~99の間で入力して下さい"
     *      }
     * }
     */
    val body
        get(): Map<String, Any> =
             mutableMapOf<String, Any>().also {
                it["id"] = this.javaClass.simpleName
                it["form"] = form
            }
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ValidationException(
    // 親のBusinessExceptionで定義済なのでval不要
    form: Map<String,Any> = emptyMap(),
    message: String? = null
): BusinessException(form = form, message = message) {
    // エラーメッセージ用のstaticなメッセージ関数を定義
    companion object {
        fun required(): String = "必須入力です"
        fun lessThan(len: Int): String = "${len}文字以下で入力して下さい"
        fun invalidEmail(): String = "メールアドレスの形式が不正です"
        fun invalidTel(): String = "電話番号の形式が不正です"
        fun lessThanLimit(limit: Int): String = "${limit}個まで選択可能です"
    }
}
