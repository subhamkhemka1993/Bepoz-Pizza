package com.android.bepozpizza.domain

import com.android.bepozpizza.data.dtos.OfferConfigItem
import com.android.bepozpizza.data.dtos.UserType
import com.android.bepozpizza.domain.models.PizzaUIItem
import javax.inject.Inject

class CheckoutManagerImpl @Inject constructor() : CheckoutManager {

    private val priceConfigList = arrayListOf<OfferConfigItem>()
    private val itemList = arrayListOf<PizzaUIItem>()

    private var _pricingConfigForUser: OfferConfigItem? = null
    private var isOfferApplied: Boolean = false


    override fun addPricingRule(offerConfigItem: OfferConfigItem) {
        priceConfigList.add(offerConfigItem)
    }

    override fun addPricingRules(offerConfigItems: List<OfferConfigItem>) {
        priceConfigList.clear()
        priceConfigList.addAll(offerConfigItems)
    }

    override fun addItem(pizza: PizzaUIItem) {
        itemList.add(pizza)
    }

    override fun addItems(pizzas: List<PizzaUIItem>) {
        itemList.clear()
        itemList.addAll(pizzas)
    }

    override fun calculatePrice(): Float {
        isOfferApplied = false
        return _pricingConfigForUser?.let { offerConfigItem ->
            val filteredPizzas = itemList.filter { it.pizzaType == offerConfigItem.pizzaType }.getOrNull(0)
            filteredPizzas?.let { pizzas ->
                val totalItemQty = pizzas.itemQty
                val actualQtyForBilling = if (offerConfigItem.comboOffer) {
                    val minItemQuantityPurchase = offerConfigItem.minItemQuantityPurchase
                    val dealCount = totalItemQty / minItemQuantityPurchase
                    if (dealCount > 0) {
                        isOfferApplied = true
                    }
                    totalItemQty.minus(dealCount)
                } else {
                    totalItemQty
                }
                var totalPrice = if (offerConfigItem.discountPrice == 0f) {
                    actualQtyForBilling.times(pizzas.price)
                } else {
                    isOfferApplied = true
                    actualQtyForBilling.times(offerConfigItem.discountPrice)
                }
                val pizzaUIItemList = itemList.filterNot { it.pizzaType == offerConfigItem.pizzaType }
                totalPrice += calculateNonDiscountedPizzas(pizzaUIItemList)
                totalPrice
            } ?: run {
                calculateNonDiscountedPizzas(itemList)
            }
        } ?: run {
            calculateNonDiscountedPizzas(itemList)
        }
    }

    override fun setUserType(userType: UserType?) {
        _pricingConfigForUser = priceConfigList.filter { it.userType == userType }.getOrNull(0)
    }

    override fun isOfferApplied(): Boolean {
        return isOfferApplied
    }

    override fun getDisplayOfferName(): String? {
        return _pricingConfigForUser?.offerName
    }

    private fun calculateNonDiscountedPizzas(listOfPizzaItem: List<PizzaUIItem>): Float {
        var totalPrice = 0.0f
        for (pizza in listOfPizzaItem) {
            totalPrice += pizza.price.times(pizza.itemQty)
        }
        return totalPrice
    }
}