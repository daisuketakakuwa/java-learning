package validator.customvalidator.custom_validator.model

import validator.customvalidator.custom_validator.component.throwIfNotEmpty
import validator.customvalidator.custom_validator.exception.ValidationException

data class PostClientRequest (
    val name: String,
    val email: String
) {

    companion object {
        private const val MAX_CHARS_NAME = 10
    }

    fun validate() {
        val errorMap = mutableMapOf<String, Any>()

        // 最大文字数チェック
        if (name.length > MAX_CHARS_NAME) {
            errorMap["name"] = ValidationException.lessThan(MAX_CHARS_NAME)
        }

        // フォーマットチェック
        if (!email.contains("@")) {
            errorMap["email"] = ValidationException.invalidEmail()
        }

        if (errorMap.isNotEmpty()) errorMap.throwIfNotEmpty("不正なリクエストが検知されました。")
    }
}
