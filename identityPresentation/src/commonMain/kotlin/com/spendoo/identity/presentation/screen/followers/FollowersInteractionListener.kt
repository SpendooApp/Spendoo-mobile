package com.spendoo.identity.presentation.screen.followers

interface FollowersInteractionListener {
    fun onClickBack()
    fun onClickApprove(followerId: String)
    fun onClickReject(followerId: String)
    fun onClickRemove(followerId: String)
    fun onLoadMorePendingRequests()
    fun onLoadMoreFollowers()
    fun onReload()
}
