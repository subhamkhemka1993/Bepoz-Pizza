package com.android.bepozpizza.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.android.bepozpizza.R
import com.android.bepozpizza.presentation.login.LoginBottomSheet
import com.android.bepozpizza.presentation.login.LoginStateViewModel

abstract class BaseActivity<VM : ViewBinding> : AppCompatActivity() {

    private var _binding: VM? = null

    protected val binding
        get() = _binding!!

    private val loginViewModel: LoginStateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        initLiveDataObserver()
        initUIBinding()
    }

    open fun initLiveDataObserver() {
        loginViewModel.loginStateLiveData.observe(this) {
            refreshPage()
        }
    }

    open fun refreshPage() {
    }

    abstract fun getViewBinding(): VM

    abstract fun initUIBinding()

    fun showLogin() {
        val loginBottomSheet = LoginBottomSheet()
        loginBottomSheet.showNow(supportFragmentManager, LoginBottomSheet.TAG)
    }

    fun showLogoutAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.label_logout)
        builder.setMessage(R.string.logout_message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(R.string.label_logout) { dialogInterface, which ->
            loginViewModel.logout()
            refreshPage()
        }
        builder.setNegativeButton(android.R.string.cancel) { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}