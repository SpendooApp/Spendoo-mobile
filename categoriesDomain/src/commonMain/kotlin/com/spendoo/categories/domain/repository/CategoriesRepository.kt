package com.spendoo.categories.domain.repository

import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.entity.category.CategorySummary
import com.spendoo.categories.domain.entity.category.CreateCategory
import com.spendoo.categories.domain.utils.PageQuery
import com.spendoo.categories.domain.utils.PagedData
import com.spendoo.categories.domain.entity.category.UpdateCategory
import com.spendoo.categories.domain.entity.transaction.CategorySpending

interface CategoriesRepository {
    suspend fun getCategories(pageQuery: PageQuery): PagedData<Category>
    suspend fun createCategory(request: CreateCategory)
    suspend fun getCategory(categoryId: String): Category
    suspend fun updateCategory(categoryId: String, request: UpdateCategory)
    suspend fun deleteCategory(categoryId: String)
    suspend fun getCategoriesSummary(): CategorySummary
    suspend fun getTopSpending(pageQuery: PageQuery): PagedData<CategorySpending>
}

