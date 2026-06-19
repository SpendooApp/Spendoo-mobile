package com.spendoo.identity.domain.useCase.validation.auth

import com.spendoo.shared.domain.utils.getToday
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlin.time.ExperimentalTime

class ValidationUseCase {
    private val maxFileSize = 5 * 1024 * 1024 // 5 MB

    fun validateEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    fun validateFullName(fullName: String): Boolean {
        val parts = fullName.trim().split(" ")
        return (parts.size < 2 || parts.any { it.length < 2 }).not()
    }

    fun validatePassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    fun validateOtp(otp: String): Boolean {
        return otpRegex.matches(otp)
    }

    fun validateFileSizes(file: ByteArray): Boolean {
        return file.size <= maxFileSize
    }

    @OptIn(ExperimentalTime::class)
    fun validateAge(date: LocalDate): Boolean {
        val today = getToday()
        val yearAdjustment =
            if (today.month < date.month || (today.month == date.month && today.day <= date.day)) 1 else 0
        val age = today.year - date.year - yearAdjustment

        return age >= MIN_AGE
    }

    @OptIn(ExperimentalTime::class)
    fun getMaximumAllowedRegistrationDate(): LocalDate {
        return getToday()
            .minus(MIN_AGE.toLong(), DateTimeUnit.YEAR)
            .minus(1, DateTimeUnit.DAY)
    }

    private companion object {
        private const val MIN_AGE = 8
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        val otpRegex = "^\\d{5}$".toRegex()
        val passwordRegex =  """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$""".toRegex()
    }
}
