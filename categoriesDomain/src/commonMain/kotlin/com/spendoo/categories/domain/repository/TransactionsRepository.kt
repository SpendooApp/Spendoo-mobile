package com.spendoo.categories.domain.repository

import com.spendoo.categories.domain.entity.transaction.BalanceSummary
import com.spendoo.categories.domain.entity.transaction.CreateExpense
import com.spendoo.categories.domain.entity.transaction.CreateIncome
import com.spendoo.categories.domain.entity.transaction.ReadyTransactionEntry
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import com.spendoo.categories.domain.entity.transaction.Transaction
import com.spendoo.categories.domain.entity.transaction.UpdateTransaction

interface TransactionsRepository {
    suspend fun getTransactions(pageQuery: PageQuery): PagedData<Transaction>
    suspend fun createIncome(request: CreateIncome)
    suspend fun createExpense(request: CreateExpense)
    suspend fun getTransaction(transactionId: String): Transaction
    suspend fun updateTransaction(transactionId: String, request: UpdateTransaction)
    suspend fun deleteTransaction(transactionId: String)
    suspend fun getBalanceSummary(): BalanceSummary
    suspend fun getTransactionsByRange(
        startDate: String,
        endDate: String,
        pageQuery: PageQuery
    ): PagedData<Transaction>


    suspend fun getReadyInputFromVoice(file: ByteArray): List<ReadyTransactionEntry>
    suspend fun getReadyInputFromImage(file: ByteArray): List<ReadyTransactionEntry>
}
