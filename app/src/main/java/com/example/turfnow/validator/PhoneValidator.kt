package com.example.turfnow.validator

import android.text.TextUtils
import com.example.turfnow.R
import com.example.turfnow.validator.base.BaseValidator
import com.example.turfnow.validator.base.ValidateResult

class PhoneValidator(val phone: String) : BaseValidator() {
    val standardPhonePattern = Regex("^[6-9]\\d{9}\$")
    override fun validate(): ValidateResult {
        val isValid =
            !TextUtils.isEmpty(phone) && phone.matches(standardPhonePattern)
        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success else R.string.text_validation_error_phone
        )
    }
}