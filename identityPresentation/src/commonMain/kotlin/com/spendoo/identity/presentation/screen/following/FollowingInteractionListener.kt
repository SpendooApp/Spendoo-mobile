package com.spendoo.identity.presentation.screen.following

interface FollowingInteractionListener {
    fun onClickBack()
    fun onReload()
    fun onClickUnfollow(userId: String)
    fun onClickUser(userId: String, userName: String, imageUrl: String?)
    fun onLoadMoreFollowing()
}
