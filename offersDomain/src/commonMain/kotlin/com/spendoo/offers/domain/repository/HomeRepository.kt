package com.spendoo.offers.domain.repository

import com.spendoo.offers.domain.entity.Offer
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData

interface OffersRepository {
    suspend fun getOffers(query: PageQuery, keywords: List<String>, maxPrice: Double? = null): PagedData<Offer>
}