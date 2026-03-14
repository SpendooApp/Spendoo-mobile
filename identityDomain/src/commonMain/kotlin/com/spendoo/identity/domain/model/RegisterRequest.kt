package com.spendoo.identity.domain.model

import com.spendoo.identity.domain.entity.Gender
import kotlinx.datetime.LocalDate

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val birthDate: LocalDate,
    val gender: Gender,
)