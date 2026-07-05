package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.FollowCode

interface FollowCodeRepository {
    suspend fun generateFollowCode(): FollowCode
}
