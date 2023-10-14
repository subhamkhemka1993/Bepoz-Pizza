package com.android.bepozpizza.data.dtos

import com.android.bepozpizza.R

data class PizzaItem(
    val pizzaType: PizzaType,
    val imgRes: Int = R.drawable.iv_pizza,
    val price: Float,
    val name: String,
    val description: String,
)

enum class PizzaType(val pizzaType: String) {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large")
}