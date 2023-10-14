package com.android.bepozpizza.data

import android.content.Context
import android.content.SharedPreferences
import com.android.bepozpizza.data.dtos.UserType
import com.android.bepozpizza.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) : PreferenceStorage {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.APP_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    override fun setUserLoginStatus(loginStatus: Boolean) {
        setBoolean(key = Constants.IS_LOGGED_IN, loginStatus)
    }

    override fun getUserLoginStatus(): Boolean {
        return getBoolean(key = Constants.IS_LOGGED_IN)
    }

    override fun getUser(): UserType? {
        val userType = getString(Constants.USER_TYPE) ?: ""
        return try {
            UserType.valueOf(userType.uppercase())
        } catch (e: Exception) {
            null
        }
    }

}