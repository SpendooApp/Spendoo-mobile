package com.spendoo.identity.domain.model

data class Profile(
    val id: String,
    val fullName: String,
    val birthDate: String,
    val gender: String,
    val imageUrl: String? = null,
)

