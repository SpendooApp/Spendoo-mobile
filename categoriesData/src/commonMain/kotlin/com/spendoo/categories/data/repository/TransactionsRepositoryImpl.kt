package com.spendoo.categories.data.repository

import com.spendoo.categories.data.dataSource.remote.dto.BasePagedData
import com.spendoo.categories.data.dataSource.remote.dto.category.BalanceSummaryDto
import com.spendoo.categories.data.dataSource.remote.dto.toPagedData
import com.spendoo.categories.data.dataSource.remote.dto.transaction.TransactionDto
import com.spendoo.categories.data.dataSource.remote.dto.transaction.toDomain
import com.spendoo.categories.data.dataSource.remote.dto.transaction.toDto
import com.spendoo.categories.data.dataSource.remote.endpoint.TransactionsEndpoints
import com.spendoo.categories.data.shared.BaseGateway
import com.spendoo.categories.domain.entity.transaction.BalanceSummary
import com.spendoo.categories.domain.entity.transaction.CreateExpense
import com.spendoo.categories.domain.entity.transaction.CreateIncome
import com.spendoo.categories.domain.entity.transaction.ReadyTransactionEntry
import com.spendoo.categories.domain.entity.transaction.Transaction
import com.spendoo.categories.domain.entity.transaction.UpdateTransaction
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.categories.domain.utils.PageQuery
import com.spendoo.categories.domain.utils.PagedData
import com.spendoo.categories.domain.utils.orEmpty
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.delay

class TransactionsRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), TransactionsRepository {

    override suspend fun getTransactions(pageQuery: PageQuery): PagedData<Transaction> {
        val response = tryToExecute<BasePagedData<TransactionDto>> {
            get(TransactionsEndpoints.TRANSACTIONS) {
                url {
                    parameters.append("page", pageQuery.page.toString())
                    parameters.append("size", pageQuery.size.toString())
                    pageQuery.sort?.forEach { sort -> parameters.append("sort", sort) }
                }
            }
        }
        return response.toPagedData { it.toDomain() }.orEmpty()
    }

    override suspend fun createIncome(request: CreateIncome) {
        tryToExecute<Unit> {
            post(TransactionsEndpoints.INCOME) {
                setBody(request.toDto())
            }
        }
    }

    override suspend fun createExpense(request: CreateExpense) {
        tryToExecute<Unit> {
            post(TransactionsEndpoints.EXPENSE) {
                setBody(request.toDto())
            }
        }
    }

    override suspend fun getTransaction(transactionId: String): Transaction {
        val response = tryToExecute<TransactionDto> {
            get(TransactionsEndpoints.TRANSACTIONS) {
                url { appendPathSegments(transactionId) }
            }
        }
        return response.toDomain()
    }

    override suspend fun updateTransaction(transactionId: String, request: UpdateTransaction) {
        tryToExecute<Unit> {
            patch(TransactionsEndpoints.TRANSACTIONS) {
                url { appendPathSegments(transactionId) }
                setBody(request.toDto())
            }
        }
    }

    override suspend fun deleteTransaction(transactionId: String) {
        tryToExecute<Unit> {
            delete(TransactionsEndpoints.TRANSACTIONS) {
                url { appendPathSegments(transactionId) }
            }
        }
    }

    override suspend fun getBalanceSummary(): BalanceSummary {
        val response = tryToExecute<BalanceSummaryDto> {
            get(TransactionsEndpoints.SUMMARY)
        }
        return BalanceSummary(
            totalBalance = response.totalBalance,
            income = response.income,
            expenses = response.expenses,
        )
    }

    override suspend fun getTransactionsByRange(
        startDate: String,
        endDate: String,
        pageQuery: PageQuery,
    ): PagedData<Transaction> {
        val response = tryToExecute<BasePagedData<TransactionDto>> {
            get(TransactionsEndpoints.RANGE) {
                url {
                    parameters.append("startDate", startDate)
                    parameters.append("endDate", endDate)
                    parameters.append("page", pageQuery.page.toString())
                    parameters.append("size", pageQuery.size.toString())
                    pageQuery.sort?.forEach { sort -> parameters.append("sort", sort) }
                }
            }
        }
        return response.toPagedData { it.toDomain() }.orEmpty()
    }


    override suspend fun getReadyInputFromVoice(file: ByteArray): List<ReadyTransactionEntry> {
//        val response = tryToExecute<List<ReadyTransactionEntryDto>> {
//            submitFormWithBinaryData(
//                url = TransactionsEndpoints.VOICE_TO_TRANSACTION,
//                formData = formData {
//                    append("file", file, Headers.build {
//                        append(HttpHeaders.ContentDisposition, "filename=\"voice.m4a\"")
//                    })
//                }
//            )
//        }
//        return response.map { it.toDomain() }

        //TODO: Implement voice to transaction endpoint and remove mock implementation
        delay(2000)
        return listOf(
            ReadyTransactionEntry(
                title = "Grocery Shopping",
                amount = 150.0,
                categoryId = "1",
                note = "Bought fruits and vegetables"
            ),
            ReadyTransactionEntry(
                title = "Dinner at Restaurant",
                amount = 75.0,
                categoryId = "2",
                note = "Had dinner with friends"
            ),
            ReadyTransactionEntry(
                title = "Monthly Subscription",
                amount = 20.0,
                categoryId = "3",
                note = "Subscribed to a streaming service"
            ),
            ReadyTransactionEntry(
                title = "Gym Membership",
                amount = 50.0,
                categoryId = "4",
                note = "Monthly gym membership fee"
            ),
             ReadyTransactionEntry(
                title = "Online Course",
                amount = 100.0,
                categoryId = "5",
                note = "Purchased an online course for learning"
            )
        )
    }

    override suspend fun getReadyInputFromImage(file: ByteArray): List<ReadyTransactionEntry> {
//        val response = tryToExecute<List<ReadyTransactionEntryDto>> {
//            submitFormWithBinaryData(
//                url = TransactionsEndpoints.IMAGE_TO_TRANSACTION,
//                formData = formData {
//                    append("file", file, Headers.build {
//                        append(HttpHeaders.ContentDisposition, "filename=\"image.jpg\"")
//                    })
//                }
//            )
//        }
//        return response.map { it.toDomain() }
        //TODO: Implement image to transaction endpoint and remove mock implementation
        delay(2000)
        return listOf(
            ReadyTransactionEntry(
                title = "Grocery Shopping",
                amount = 150.0,
                categoryId = "1",
                note = "Bought fruits and vegetables"
            ),
            ReadyTransactionEntry(
                title = "Dinner at Restaurant",
                amount = 75.0,
                categoryId = "2",
                note = "Had dinner with friends"
            ),
            ReadyTransactionEntry(
                title = "Monthly Subscription",
                amount = 20.0,
                categoryId = "3",
                note = "Subscribed to a streaming service"
            ),
            ReadyTransactionEntry(
                title = "Gym Membership",
                amount = 50.0,
                categoryId = "4",
                note = "Monthly gym membership fee"
            ),
            ReadyTransactionEntry(
                title = "Online Course",
                amount = 100.0,
                categoryId = "5",
                note = "Purchased an online course for learning"
            )
        )
    }
}
