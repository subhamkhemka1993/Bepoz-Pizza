package com.android.bepozpizza.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.bepozpizza.R
import com.android.bepozpizza.base.BaseViewModel
import com.android.bepozpizza.data.dtos.UserType
import com.android.bepozpizza.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginStateViewModel @Inject constructor() : BaseViewModel() {

    private var _loginStateLiveData = MutableLiveData<Boolean>()
    val loginStateLiveData: LiveData<Boolean> = _loginStateLiveData

    fun onLoginStateChange(isLoggedIn: Boolean, checkedRadioButtonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userType = when (checkedRadioButtonId) {
                R.id.rb_amazon -> UserType.AMAZON.toString()
                R.id.rb_facebook -> UserType.FACEBOOK.toString()
                R.id.rb_microsoft -> UserType.MICROSOFT.toString()
                else -> ""
            }
            sessionManager.setUserLoginStatus(isLoggedIn)
            sessionManager.setString(Constants.USER_TYPE, userType)
            _loginStateLiveData.postValue(isLoggedIn)
        }
    }
}