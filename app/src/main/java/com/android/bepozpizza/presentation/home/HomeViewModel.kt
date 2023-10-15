package com.android.bepozpizza.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.bepozpizza.R
import com.android.bepozpizza.base.BaseViewModel
import com.android.bepozpizza.data.repositories.PizzaRepository
import com.android.bepozpizza.data.repositories.PricingRepository
import com.android.bepozpizza.data.toPizzaUIItem
import com.android.bepozpizza.domain.CheckoutManager
import com.android.bepozpizza.domain.models.PizzaUIItem
import com.android.bepozpizza.utils.UIText
import com.android.bepozpizza.utils.getFormattedPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pizzaRepository: PizzaRepository,
    private val pricingRepository: PricingRepository,
    private val checkoutManager: CheckoutManager,
) : BaseViewModel() {

    private val listOfPizzaItem = arrayListOf<PizzaUIItem>()

    private var _pizzaUIItemListLiveData = MutableLiveData<List<PizzaUIItem>>()
    val pizzaUIItemListLiveData: LiveData<List<PizzaUIItem>> = _pizzaUIItemListLiveData

    private var _checkoutInfoLiveData = MutableLiveData<Triple<Boolean, String, Boolean>>()
    val checkoutInfoLiveData: LiveData<Triple<Boolean, String, Boolean>> = _checkoutInfoLiveData

    private var _progressDialogLiveData = MutableLiveData<Pair<Boolean, UIText>>()
    val progressDialogLiveData: LiveData<Pair<Boolean, UIText>> = _progressDialogLiveData

    private var _displayOfferMessageLiveData = MutableLiveData<String>()
    val displayOfferMessageLiveData: LiveData<String> = _displayOfferMessageLiveData

    init {
        getPricingConfig()
        _checkoutInfoLiveData.value = Triple(false, getFormattedPrice(currencySymbol, 0.00f), false)
    }

    private fun getPricingConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            val pricingConfigurations = pricingRepository.getPricingConfiguration()
            checkoutManager.addPricingRules(pricingConfigurations)
            checkoutManager.setUserType(userType)
            getDisplayOfferInfo()
        }
    }

    fun getPizzaListing() {
        viewModelScope.launch(Dispatchers.IO) {
            _progressDialogLiveData.postValue(Pair(true, UIText.ResourceString(R.string.message_fetching_product_listing)))
            listOfPizzaItem.clear()
            val pizzaListing = pizzaRepository.getPizzaListing()
            val pizzaUIItemList = pizzaListing.map { it.toPizzaUIItem(currencySymbol) }
            listOfPizzaItem.addAll(pizzaUIItemList)
            _pizzaUIItemListLiveData.postValue(listOfPizzaItem)
            _progressDialogLiveData.postValue(Pair(false, UIText.ResourceString(R.string.message_fetching_product_listing)))
        }
    }

    fun addOrRemoveItem(position: Int, addItem: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (position in 0..listOfPizzaItem.size) {
                val pizzaUIItem = listOfPizzaItem[position]
                val itemQty = pizzaUIItem.itemQty
                val newQty = if (addItem) {
                    itemQty.plus(1)
                } else {
                    if (itemQty == 0) {
                        0
                    } else {
                        itemQty.minus(1)
                    }
                }
                val newPizzaItem = pizzaUIItem.copy(itemQty = newQty)
                listOfPizzaItem[position] = newPizzaItem
                _pizzaUIItemListLiveData.postValue(listOfPizzaItem)
                calculateTotalPrice()
            }
        }
    }

    private fun calculateTotalPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            checkoutManager.addItems(listOfPizzaItem.filter { it.itemQty > 0 })
            val calculatePricedPrice = checkoutManager.calculatePrice()
            val isOfferApplied = checkoutManager.isOfferApplied()
            _checkoutInfoLiveData.postValue(Triple(calculatePricedPrice > 0, getFormattedPrice(currencySymbol, calculatePricedPrice), isOfferApplied))
        }
    }

    fun onLoginStatusChanged() {
        viewModelScope.launch(Dispatchers.IO) {
            checkoutManager.setUserType(userType)
            getDisplayOfferInfo()
            val filteredList = listOfPizzaItem.filter { it.itemQty > 0 }
            if (filteredList.isNotEmpty()) {
                _progressDialogLiveData.postValue(Pair(true, UIText.ResourceString(R.string.message_refreshing_prices)))
                delay(1000)
                calculateTotalPrice()
                _progressDialogLiveData.postValue(Pair(false, UIText.ResourceString(R.string.message_refreshing_prices)))
            }
        }
    }

    private fun getDisplayOfferInfo() {
        val displayOfferName = checkoutManager.getDisplayOfferName().orEmpty()
        _displayOfferMessageLiveData.postValue(displayOfferName)
    }
}