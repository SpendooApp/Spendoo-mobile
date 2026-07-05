package com.spendoo.categories.data.dataSource.remote.endpoint

object CategoriesEndpoints {
    const val CATEGORIES = "api/v1/categories"
    const val CATEGORY_SUMMARY = "api/v1/categories/summary"
    const val TOP_SPENDING = "api/v1/categories/top-spending"
}

object TransactionsEndpoints {
    const val TRANSACTIONS = "api/v1/transactions"
    const val INCOME = "api/v1/transactions/income"
    const val EXPENSE = "api/v1/transactions/expense"
    const val SUMMARY = "api/v1/transactions/summary"
    const val RANGE = "api/v1/transactions/range"
    const val VOICE_TO_TRANSACTION = "api/v1/transactions/voice/process"
    const val IMAGE_TO_TRANSACTION = "api/v1/transactions/ocr/scan"
    const val TOP_FREQUENCY_ITEMS = "api/v1/transactions/top-frequency-items"
}

