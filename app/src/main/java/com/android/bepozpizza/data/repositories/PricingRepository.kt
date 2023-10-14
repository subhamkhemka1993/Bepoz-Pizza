package com.android.bepozpizza.data.repositories

import com.android.bepozpizza.data.dtos.OfferConfigItem
import com.android.bepozpizza.data.dtos.PizzaType
import com.android.bepozpizza.data.dtos.UserType

interface PricingRepository {

    suspend fun getPricingConfiguration(): List<OfferConfigItem>
}

val listOfPricingConfig = listOf(
    OfferConfigItem(
        discountPrice = 0f,
        offerName = "Gets a 3 for 2 deal for Small Pizzas",
        userType = UserType.MICROSOFT,
        pizzaType = PizzaType.SMALL,
        comboOffer = true,
        minItemQuantityPurchase = 3,
    ),
    OfferConfigItem(
        discountPrice = 19.99f,
        offerName = "Gets a discount on Large Pizza where the price drops to $19.99 per pizza",
        userType = UserType.AMAZON,
        pizzaType = PizzaType.LARGE,
        minItemQuantityPurchase = 1,
    ),
    OfferConfigItem(
        discountPrice = 0f,
        offerName = "Get a 5 for 4 deal on Medium Pizza",
        userType = UserType.FACEBOOK,
        pizzaType = PizzaType.MEDIUM,
        comboOffer = true,
        minItemQuantityPurchase = 5,
    )
)