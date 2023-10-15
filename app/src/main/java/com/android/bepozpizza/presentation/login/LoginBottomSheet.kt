package com.android.bepozpizza.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.android.bepozpizza.databinding.BottomSheetLoginBinding
import com.android.bepozpizza.utils.hideSoftKeyboard
import com.android.bepozpizza.utils.showShortToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLoginBinding? = null

    private val binding
        get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()
    private val loginStateViewModel: LoginStateViewModel by activityViewModels()

    companion object {
        const val TAG = "LoginBottomSheet"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetLoginBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveDataObserver()
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheetInternal = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetInternal?.let { internalView ->
                BottomSheetBehavior.from(internalView).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        binding.apply {
            inputUsername.requestFocus()
            btnLoginNow.setOnClickListener {
                loginViewModel.validateUser(inputUsername.text.toString())
            }
        }
    }


    private fun initLiveDataObserver() {
        loginViewModel.loginUIState.observe(this) { loginUIState ->
            when (loginUIState) {
                is LoginUIState.EmptyUserName -> {
                    requireContext().showShortToast(loginUIState.emptyUserMessage)
                }

                is LoginUIState.LoginSuccess -> {
                    dismiss()
                    activity?.hideSoftKeyboard()
                    requireContext().showShortToast(loginUIState.loginSuccessMessage)
                    loginStateViewModel.onLoginStateChange(isLoggedIn = true, binding.rgUserType.checkedRadioButtonId)
                }
            }
        }
    }
}