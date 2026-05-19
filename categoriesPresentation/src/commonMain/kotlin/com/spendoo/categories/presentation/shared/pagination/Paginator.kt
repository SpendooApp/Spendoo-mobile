package com.spendoo.categories.presentation.shared.pagination

class Paginator<Key, Items>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Items,
    private val getNextKey: suspend (currentKey: Key, result: Items) -> Key,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (result: Items, newKey: Key) -> Unit,
    private val endReached: (currentKey: Key, result: Items) -> Boolean
) {

    private var currentKey = initialKey
    private var isMakingRequest = false
    private var isEndReached = false
    private var isErrorState = false

    suspend fun loadNextItems() {
        if (isMakingRequest || isEndReached || isErrorState) return

        isMakingRequest = true
        onLoadUpdated(true)

        runCatching {
            onRequest(currentKey)
        }.onSuccess { items ->
            isMakingRequest = false
            currentKey = getNextKey(currentKey, items)
            onSuccess(items, currentKey)
            onLoadUpdated(false)
            isEndReached = endReached(currentKey, items)
        }.onFailure {
            isMakingRequest = false
            isErrorState = true
            onError(it)
            onLoadUpdated(false)
        }
    }

    fun reset() {
        currentKey = initialKey
        isEndReached = false
        isErrorState = false
        isMakingRequest = false
    }
}