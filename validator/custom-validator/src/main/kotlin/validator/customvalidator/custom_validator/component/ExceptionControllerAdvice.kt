package validator.customvalidator.custom_validator.component

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import validator.customvalidator.custom_validator.exception.BusinessException
import validator.customvalidator.custom_validator.exception.ValidationException

/**
 * 全RestControllerに対する共通処理の適用
 */
@ControllerAdvice(annotations = [RestController::class])
class ExceptionControllerAdvice: ResponseEntityExceptionHandler() {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException, request: WebRequest): ResponseEntity<Any>? {
        // 1. 各BusinessException実装クラスで定義されている@ResponseStatusの値を取得
        val status: ResponseStatus? = ex.javaClass.getAnnotation(ResponseStatus::class.java)

        // 2. メッセージをログ出力(例外種別によってレベル変更する)
        when (ex) {
            is ValidationException ->
                LOGGER.info(ex.message)
            else ->
                LOGGER.warn(ex.message)
        }

        // 3. ResponseEntityExceptionHandlerで定義してくれてる関数でResponseEntity作成
        return super.handleExceptionInternal(
            ex,
            ex.body,
            HttpHeaders(),
            status?.value ?: HttpStatus.SERVICE_UNAVAILABLE,
            request
        )
    }

}
