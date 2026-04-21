package com.spendoo.categories.data.dataSource.remote.dto

import com.spendoo.categories.domain.utils.PagedData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BasePagedData<T>(
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("totalElements")
    val totalElements: Long?,
    @SerialName("first")
    val first: Boolean,
    @SerialName("last")
    val last: Boolean,
    @SerialName("size")
    val size: Int,
    @SerialName("content")
    val content: List<T>?,
    @SerialName("number")
    val number: Int,
    @SerialName("sort")
    val sort: SortDto,
    @SerialName("numberOfElements")
    val numberOfElements: Int,
    @SerialName("pageable")
    val pageable: PageableMetaDto,
    @SerialName("empty")
    val empty: Boolean,
)

fun <DTO, ENTITY> BasePagedData<DTO>.toPagedData(mapper: (DTO) -> ENTITY?): PagedData<ENTITY> {
    return PagedData(
        data = content?.mapNotNull(mapper) ?: emptyList(),
        totalItems = totalElements ?: 0L,
        isLastPage = last,
    )
}

@Serializable
data class PageableMetaDto(
    @SerialName("offset")
    val offset: Long,
    @SerialName("sort")
    val sort: SortDto,
    @SerialName("unpaged")
    val unpaged: Boolean,
    @SerialName("paged")
    val paged: Boolean,
    @SerialName("pageNumber")
    val pageNumber: Int,
    @SerialName("pageSize")
    val pageSize: Int,
)

@Serializable
data class SortDto(
    @SerialName("empty")
    val empty: Boolean,
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("unsorted")
    val unsorted: Boolean,
)
