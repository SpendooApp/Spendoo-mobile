package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.FollowStatus
import com.spendoo.identity.domain.model.UserSearch
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData

interface FollowRepository {
    suspend fun searchUserByCode(code: String): UserSearch
    suspend fun sendFollowRequest(followeeId: String)
    suspend fun respondToFollowRequest(followerId: String, isApproved: Boolean)
    suspend fun getFollowing(query: PageQuery): PagedData<UserSearch>
    suspend fun getFollowers(query: PageQuery): PagedData<UserSearch>
    suspend fun getPendingRequests(query: PageQuery): PagedData<UserSearch>
    suspend fun unfollowUser(followeeId: String)
    suspend fun removeFollower(followerId: String)
    suspend fun checkFollowStatus(followerId: String, followeeId: String): FollowStatus
}
