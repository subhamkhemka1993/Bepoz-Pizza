package com.android.bepozpizza.data.dtos

data class OfferConfigItem(
    val discountPrice: Float,
    val offerName: String,
    val userType: UserType,
    val pizzaType: PizzaType,
    val comboOffer: Boolean = false,
    val minItemQuantityPurchase: Int,
)