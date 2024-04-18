package com.sitaram.foodshare.utils

/**
 * Utility object containing functions to validate email addresses and phone numbers.
 * Regular expression to match the specified patterns
 */
object Validators {

    // Checks if the provided email address is valid.
    fun isValidEmailAddress(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.trim().matches(emailRegex)
    }

    // Checks if the provided phone number is valid.
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return run {
            val phoneRegex = """^\+?\d{3}\s?\d{10}$|^(97|98)\d{8}$""".toRegex()
            phoneNumber.trim().matches(phoneRegex)
        }
    }

}