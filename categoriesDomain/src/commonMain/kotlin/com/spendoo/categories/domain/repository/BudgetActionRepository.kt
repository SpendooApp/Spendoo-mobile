package com.spendoo.categories.domain.repository

interface BudgetActionRepository {
    suspend fun executeProposedAction(actionId: String)
}
