package com.android.bepozpizza.domain

import com.android.bepozpizza.data.dtos.OfferConfigItem
import com.android.bepozpizza.data.dtos.UserType
import com.android.bepozpizza.domain.models.PizzaUIItem

interface CheckoutManager {

    fun addPricingRule(offerConfigItem: OfferConfigItem)
    fun addPricingRules(offerConfigItems: List<OfferConfigItem>)
    fun addItem(pizza: PizzaUIItem)
    fun addItems(pizzas: List<PizzaUIItem>)
    fun calculatePrice(): Float
    fun setUserType(userType: UserType?)
    fun getAppliedOfferName(): String?
    fun getDisplayOfferName(): String?
}