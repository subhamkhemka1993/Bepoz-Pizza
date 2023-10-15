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
        offerName = "Hungry for a Deal? Microsoft Employees, it's your time to savor! Enjoy 3 Small Pizzas for the Price of 2 – Exclusively for You!",
        userType = UserType.MICROSOFT,
        pizzaType = PizzaType.SMALL,
        comboOffer = true,
        minItemQuantityPurchase = 3,
    ),
    OfferConfigItem(
        discountPrice = 19.99f,
        offerName = "Slice Up the Savings! Exclusive Amazon Employee Deal: Grab a Large Pizza for Just $19.99!",
        userType = UserType.AMAZON,
        pizzaType = PizzaType.LARGE,
        minItemQuantityPurchase = 1,
    ),
    OfferConfigItem(
        discountPrice = 0f,
        offerName = "Sizzle into Savings! Get Fired Up with Our 5 for 4 Pizza Deal, Exclusively for Facebook Employees. Grab a Slice of the Action Now!",
        userType = UserType.FACEBOOK,
        pizzaType = PizzaType.MEDIUM,
        comboOffer = true,
        minItemQuantityPurchase = 5,
    )
)