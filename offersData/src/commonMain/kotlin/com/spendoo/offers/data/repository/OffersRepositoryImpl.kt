package com.spendoo.offers.data.repository

import com.spendoo.offers.domain.entity.Offer
import com.spendoo.offers.domain.repository.OffersRepository
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class OffersRepositoryImpl : OffersRepository {
    override suspend fun getOffers(query: PageQuery): PagedData<Offer> {
        delay(2000.milliseconds)
        return PagedData(
            data = listOf(
                Offer(
                    "1",
                    20,
                    "https://images.unsplash.com/photo-1506744038136-46273834b3fb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8b2ZmZXJ8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60"
                ),
                Offer(
                    "2", 15,
                    "https://images.unsplash.com/photo-1519125323398-675f0ddb6308?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8b2ZmZXJ8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60"
                ),
            ),
            totalItems = 2,
            isLastPage = true
        )
    }
}
