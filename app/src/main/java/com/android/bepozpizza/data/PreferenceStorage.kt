package com.android.bepozpizza.data

import com.android.bepozpizza.data.dtos.UserType

interface PreferenceStorage {
    fun setString(key: String, value: String)
    fun getString(key: String): String?
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    fun setUserLoginStatus(loginStatus: Boolean)
    fun getUserLoginStatus(): Boolean
    fun getUser(): UserType?
    fun clear()
}