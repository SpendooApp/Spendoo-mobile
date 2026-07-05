package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.follow.response.FollowCodeDto
import com.spendoo.identity.data.dataSource.remote.dto.follow.response.toDomain
import com.spendoo.identity.domain.model.FollowCode
import com.spendoo.identity.domain.repository.FollowCodeRepository
import com.spendoo.shared.data.shared.BaseGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class FollowCodeRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), FollowCodeRepository {

    override suspend fun generateFollowCode(): FollowCode {
        val response = tryToExecute<FollowCodeDto> {
            post("api/v1/identity/follow-code/generate-code")
        }
        return response.toDomain()
    }
}
