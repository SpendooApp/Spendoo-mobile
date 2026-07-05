package com.spendoo.identity.presentation.screen.addFollower

interface AddFollowerInteractionListener {
    fun onCodeChanged(code: String)
    fun onSearchUser()
    fun onClickFollowUser(userId: String)
}
