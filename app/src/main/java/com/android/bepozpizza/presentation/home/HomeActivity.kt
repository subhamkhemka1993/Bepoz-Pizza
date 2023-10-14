package com.android.bepozpizza.presentation.home

import android.app.ProgressDialog
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bepozpizza.R
import com.android.bepozpizza.base.BaseActivity
import com.android.bepozpizza.databinding.ActivityHomeBinding
import com.android.bepozpizza.presentation.home.adapter.PizzaAdapter
import com.android.bepozpizza.utils.ItemDecorator
import com.android.bepozpizza.utils.UIText
import com.android.bepozpizza.utils.UITextParser
import com.android.bepozpizza.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var mProgressDialog: ProgressDialog? = null

    private val pizzaAdapter = PizzaAdapter { position, addItem ->
        homeViewModel.addOrRemoveItem(position, addItem)
    }

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initUIBinding() {
        binding.apply {
            rvProducts.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                itemAnimator = null
                if (itemDecorationCount == 0) {
                    addItemDecoration(ItemDecorator(context.resources.getDimensionPixelSize(R.dimen.dimen_4dp)))
                }
                adapter = pizzaAdapter
            }
            ivInfo.setOnClickListener {
                val offerMessage = homeViewModel.checkoutInfoLiveData.value?.third.orEmpty()
                if (offerMessage.isNotEmpty()) {
                    showShortToast(this@HomeActivity, offerMessage)
                }
            }
        }
        homeViewModel.getPizzaListing()
    }

    override fun initLiveDataObserver() {
        super.initLiveDataObserver()
        homeViewModel.pizzaUIItemListLiveData.observe(this) {
            pizzaAdapter.setData(it)
        }
        homeViewModel.checkoutInfoLiveData.observe(this) {
            binding.apply {
                llCheckout.isEnabled = it.first
                tvCheckoutPrice.text = it.second
                ivInfo.isVisible = it.third.isNotEmpty()
            }
        }
        homeViewModel.progressDialogLiveData.observe(this) {
            showProgressDialog(it)
        }
        homeViewModel.displayOfferMessageLiveData.observe(this) { displayOfferMessage ->
            binding.tvOffer.apply {
                isVisible = displayOfferMessage.isNotEmpty()
                text = displayOfferMessage
                isSelected = true
            }
        }
    }

    private fun showProgressDialog(it: Pair<Boolean, UIText>) {
        val progressDialog = mProgressDialog ?: ProgressDialog(this).also {
            mProgressDialog = it
        }
        if (it.first) {
            progressDialog.apply {
                setTitle(R.string.label_please_wait)
                setMessage(UITextParser(this@HomeActivity, it.second))
                show()
            }
        } else {
            progressDialog.dismiss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val loginItem = menu?.findItem(R.id.menu_item_login)
        val logoutItem = menu?.findItem(R.id.menu_item_logout)
        if (homeViewModel.isValidUser) {
            logoutItem?.isVisible = true
            loginItem?.isVisible = false
        } else {
            loginItem?.isVisible = true
            logoutItem?.isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_login -> {
                showLogin()
            }

            R.id.menu_item_logout -> {
                showLogoutAlert()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun refreshPage() {
        super.refreshPage()
        invalidateOptionsMenu()
        homeViewModel.onLoginStatusChanged()
    }
}