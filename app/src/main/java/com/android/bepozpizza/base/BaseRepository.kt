package com.android.bepozpizza.base

import com.android.bepozpizza.data.SessionManager

open class BaseRepository(private val sessionManager: SessionManager? = null) {

    suspend fun getUserType() = sessionManager?.getUser()
    suspend fun getUserLoginStatus() = sessionManager?.getUserLoginStatus()
}