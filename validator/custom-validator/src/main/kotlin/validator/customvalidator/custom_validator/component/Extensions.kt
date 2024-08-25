package validator.customvalidator.custom_validator.component

import validator.customvalidator.custom_validator.exception.ValidationException

/**
 * Mapオブジェクト付属の関数でValidationExceptionを投げられるように
 */
fun Map<String, Any>.throwIfNotEmpty(reason: String? = null) {
    if(this.isNotEmpty() || reason != null) {
        throw ValidationException(
            form = this,
            message = reason
        )
    }
}
