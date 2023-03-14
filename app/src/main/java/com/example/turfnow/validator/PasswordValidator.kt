package com.example.turfnow.validator

import com.example.turfnow.R
import com.example.turfnow.validator.base.BaseValidator
import com.example.turfnow.validator.base.ValidateResult


class PasswordValidator(val password: String) : BaseValidator() {
    private val minPasswordLength = 6
    private val maxPasswordLength = 12

    override fun validate(): ValidateResult {
        if (password.length < minPasswordLength)
            return ValidateResult(false, R.string.text_validation_error_min_pass_length)
        if (password.length > maxPasswordLength)
            return ValidateResult(false, R.string.text_validation_error_max_pass_length)
        if (!password.matches(".*\\d.*".toRegex()))
            return ValidateResult(false, R.string.text_validation_error_no_digit)
        if (!password.matches(".*[a-zA-Z].*".toRegex()))
            return ValidateResult(false, R.string.text_validation_error_no_letter)
        return ValidateResult(true, R.string.text_validation_success)
    }
}