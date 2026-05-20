package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.profile.response.ProfileDto
import com.spendoo.identity.data.dataSource.remote.dto.profile.response.ProfileImageDto
import com.spendoo.identity.data.dataSource.remote.dto.profile.response.toDomain
import com.spendoo.identity.data.shared.BaseGateway
import com.spendoo.identity.domain.model.Profile
import com.spendoo.identity.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.delay

class ProfileRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), ProfileRepository {

    override suspend fun getProfile(): Profile {
        val response = tryToExecute<ProfileDto> {
            get(PROFILE_ENDPOINT)
        }
        return response.toDomain()
    }

    override suspend fun updateProfileImage(fileBytes: ByteArray, fileName: String): String {
        val response = tryToExecute<ProfileImageDto> {
            patch(UPDATE_IMAGE_ENDPOINT) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                key = "file",
                                value = fileBytes,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=\"$fileName\""
                                    )
                                }
                            )
                        }
                    )
                )
            }
        }
        return response.imageUrl
    }

    override suspend fun deleteProfileImage() {
        tryToExecute<Unit> {
            delete(UPDATE_IMAGE_ENDPOINT)
        }
    }

    override suspend fun getNotificationsCount(): Int {
        delay(2000)
        return 5 //TODO: Implement this method when the endpoint is ready
    }

    companion object {
        const val PROFILE_ENDPOINT = "api/v1/identity/profile"
        const val UPDATE_IMAGE_ENDPOINT = "api/v1/identity/profile/image"
    }
}

