package com.android.bepozpizza.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.bepozpizza.R
import com.android.bepozpizza.base.BaseViewModel
import com.android.bepozpizza.utils.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    private var _loginUIState = MutableLiveData<LoginUIState>()
    val loginUIState: LiveData<LoginUIState> = _loginUIState

    fun validateUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (userName.trim().isEmpty()) {
                _loginUIState.postValue(LoginUIState.EmptyUserName(UIText.ResourceString(R.string.empty_user_name_message)))
            } else {
                _loginUIState.postValue(LoginUIState.LoginSuccess(UIText.ResourceStringWithArg(R.string.welcome_message, userName.trim())))
            }
        }
    }
}