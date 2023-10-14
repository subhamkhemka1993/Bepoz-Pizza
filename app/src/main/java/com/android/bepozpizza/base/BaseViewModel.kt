package com.android.bepozpizza.base

import androidx.lifecycle.ViewModel
import com.android.bepozpizza.data.SessionManager
import com.android.bepozpizza.utils.Constants
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    protected lateinit var sessionManager: SessionManager

    val isValidUser
        get() = sessionManager.getUserLoginStatus()

    fun logout() {
        sessionManager.setUserLoginStatus(false)
        sessionManager.setString(Constants.USER_TYPE, "")
    }

    val currencySymbol by lazy { "$" }

    val userType
        get() = sessionManager.getUser()

}