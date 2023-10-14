package com.android.bepozpizza.domain.models

import com.android.bepozpizza.data.dtos.PizzaType


data class PizzaUIItem(
    val pizzaType: PizzaType,
    val imgRes: Int,
    val price: Float,
    val displayPrice: String,
    val name: String,
    val description: String,
    var itemQty: Int = 0,
)