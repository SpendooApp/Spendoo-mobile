package com.spendoo.identity.domain.model

data class UserSearch(
    val userId: String,
    val fullName: String,
    val imageUrl: String?
)
