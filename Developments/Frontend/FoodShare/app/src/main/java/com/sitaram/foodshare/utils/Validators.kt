package com.sitaram.foodshare.utils

object Validators {

    fun isValidEmailAddress(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.trim().matches(emailRegex)
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Regular expression to match the specified phone number patterns
        val phoneRegex = """^\+?\d{3}\s?\d{10}$|^(97|98)\d{8}$""".toRegex()
        return phoneNumber.trim().matches(phoneRegex)
    }

}