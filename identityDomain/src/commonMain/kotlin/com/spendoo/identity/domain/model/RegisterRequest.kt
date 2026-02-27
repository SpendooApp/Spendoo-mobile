package com.spendoo.identity.domain.model

import com.spendoo.identity.domain.entity.Gender
import kotlinx.datetime.LocalDate

data class RegisterRequest(
    val email: String,
    val username: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val password: String
)