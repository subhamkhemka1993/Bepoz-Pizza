package com.android.bepozpizza.presentation.login

import com.android.bepozpizza.utils.UIText

sealed class LoginUIState {

    data class EmptyUserName(val emptyUserMessage: UIText) : LoginUIState()
    data class LoginSuccess(val loginSuccessMessage: UIText) : LoginUIState()
}
