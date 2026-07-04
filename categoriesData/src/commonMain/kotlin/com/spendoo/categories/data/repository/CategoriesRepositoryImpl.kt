package com.spendoo.categories.data.repository

import com.spendoo.shared.data.dataSource.remote.dto.BasePagedData
import com.spendoo.categories.data.dataSource.remote.dto.category.CategoryDto
import com.spendoo.categories.data.dataSource.remote.dto.category.CategorySummaryDto
import com.spendoo.categories.data.dataSource.remote.dto.category.toDomain
import com.spendoo.categories.data.dataSource.remote.dto.category.toDto
import com.spendoo.shared.data.dataSource.remote.dto.toPagedData
import com.spendoo.categories.data.dataSource.remote.endpoint.CategoriesEndpoints
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.entity.category.CategorySummary
import com.spendoo.categories.domain.entity.category.CreateCategory
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import com.spendoo.categories.domain.entity.category.UpdateCategory
import com.spendoo.categories.domain.entity.transaction.CategorySpending
import com.spendoo.categories.data.dataSource.remote.dto.category.CategorySpendingDto
import com.spendoo.shared.domain.utils.orEmpty
import com.spendoo.categories.domain.repository.CategoriesRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments

class CategoriesRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), CategoriesRepository {

    override suspend fun getCategories(pageQuery: PageQuery): PagedData<Category> {
        val response = tryToExecute<BasePagedData<CategoryDto>> {
            get(CategoriesEndpoints.CATEGORIES) {
                url {
                    parameters.append("page", pageQuery.page.toString())
                    parameters.append("size", pageQuery.size.toString())
                    pageQuery.sort?.forEach { sort -> parameters.append("sort", sort) }
                }
            }
        }
        return response.toPagedData { it.toDomain() }.orEmpty()
    }

    override suspend fun createCategory(request: CreateCategory) {
        tryToExecute<Unit> {
            post(CategoriesEndpoints.CATEGORIES) {
                setBody(request.toDto())
            }
        }
    }

    override suspend fun getCategory(categoryId: String): Category {
        val response = tryToExecute<CategoryDto> {
            get(CategoriesEndpoints.CATEGORIES) {
                url { appendPathSegments(categoryId) }
            }
        }
        return response.toDomain()
    }

    override suspend fun updateCategory(categoryId: String, request: UpdateCategory) {
        tryToExecute<Unit> {
            patch(CategoriesEndpoints.CATEGORIES) {
                url { appendPathSegments(categoryId) }
                setBody(request.toDto())
            }
        }
    }

    override suspend fun deleteCategory(categoryId: String) {
        tryToExecute<Unit> {
            delete(CategoriesEndpoints.CATEGORIES) {
                url { appendPathSegments(categoryId) }
            }
        }
    }

    override suspend fun getCategoriesSummary(): CategorySummary {
        return tryToExecute<CategorySummaryDto> {
            get(CategoriesEndpoints.CATEGORY_SUMMARY)
        }.toDomain()
    }

    override suspend fun getTopSpending(pageQuery: PageQuery): PagedData<CategorySpending> {
        val response = tryToExecute<BasePagedData<CategorySpendingDto>> {
            get(CategoriesEndpoints.TOP_SPENDING) {
                url {
                    parameters.append("page", pageQuery.page.toString())
                    parameters.append("size", pageQuery.size.toString())
                    pageQuery.sort?.forEach { sort -> parameters.append("sort", sort) }
                }
            }
        }
        return response.toPagedData { it.toDomain() }.orEmpty()
    }
}

