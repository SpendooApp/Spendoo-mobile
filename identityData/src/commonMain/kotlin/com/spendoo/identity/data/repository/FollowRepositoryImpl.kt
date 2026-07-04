package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.follow.response.FollowStatusDto
import com.spendoo.identity.data.dataSource.remote.dto.follow.response.UserSearchDto
import com.spendoo.identity.data.dataSource.remote.dto.follow.response.toDomain
import com.spendoo.identity.domain.model.FollowStatus
import com.spendoo.identity.domain.model.UserSearch
import com.spendoo.identity.domain.repository.FollowRepository
import com.spendoo.shared.data.dataSource.remote.dto.BasePagedData
import com.spendoo.shared.data.dataSource.remote.dto.toPagedData
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put

class FollowRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), FollowRepository {

    override suspend fun searchUserByCode(code: String): UserSearch {
        val response = tryToExecute<UserSearchDto> {
            get("api/v1/identity/follows/search") {
                parameter("code", code)
            }
        }
        return response.toDomain()
    }

    override suspend fun sendFollowRequest(followeeId: String) {
        tryToExecute<Unit> {
            post("api/v1/identity/follows/request/$followeeId")
        }
    }

    override suspend fun respondToFollowRequest(followerId: String, isApproved: Boolean) {
        tryToExecute<Unit> {
            put("api/v1/identity/follows/request/$followerId/respond") {
                parameter("isApproved", isApproved)
            }
        }
    }

    override suspend fun getFollowing(query: PageQuery): PagedData<UserSearch> {
        val response = tryToExecute<BasePagedData<UserSearchDto>> {
            get("api/v1/identity/follows/following") {
                parameter("page", query.page)
                parameter("size", query.size)
            }
        }
        return response.toPagedData { it.toDomain() }
    }

    override suspend fun getFollowers(query: PageQuery): PagedData<UserSearch> {
        val response = tryToExecute<BasePagedData<UserSearchDto>> {
            get("api/v1/identity/follows/followers") {
                parameter("page", query.page)
                parameter("size", query.size)
            }
        }
        return response.toPagedData { it.toDomain() }
    }

    override suspend fun getPendingRequests(query: PageQuery): PagedData<UserSearch> {
        val response = tryToExecute<BasePagedData<UserSearchDto>> {
            get("api/v1/identity/follows/requests") {
                parameter("page", query.page)
                parameter("size", query.size)
            }
        }
        return response.toPagedData { it.toDomain() }
    }

    override suspend fun unfollowUser(followeeId: String) {
        tryToExecute<Unit> {
            delete("api/v1/identity/follows/$followeeId/unfollow")
        }
    }

    override suspend fun removeFollower(followerId: String) {
        tryToExecute<Unit> {
            delete("api/v1/identity/follows/$followerId/remove")
        }
    }

    override suspend fun checkFollowStatus(followerId: String, followeeId: String): FollowStatus {
        val response = tryToExecute<FollowStatusDto> {
            get("api/v1/identity/follows/check-status") {
                parameter("followerId", followerId)
                parameter("followeeId", followeeId)
            }
        }
        return response.toDomain()
    }
}
