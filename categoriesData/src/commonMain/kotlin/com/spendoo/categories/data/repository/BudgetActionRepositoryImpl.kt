package com.spendoo.categories.data.repository

import com.spendoo.categories.domain.repository.BudgetActionRepository
import com.spendoo.shared.data.shared.BaseGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class BudgetActionRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), BudgetActionRepository {

    override suspend fun executeProposedAction(actionId: String) {
        tryToExecute<Unit> {
            post("api/v1/budget-actions/$actionId/execute")
        }
    }
}
