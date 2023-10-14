package com.android.bepozpizza.data

import com.android.bepozpizza.data.dtos.PizzaItem
import com.android.bepozpizza.domain.models.PizzaUIItem
import com.android.bepozpizza.utils.getFormattedPrice


fun PizzaItem.toPizzaUIItem(currencySymbol: String): PizzaUIItem {
    return PizzaUIItem(pizzaType = pizzaType,
        imgRes = imgRes,
        price = price,
        displayPrice = getFormattedPrice(currencySymbol, price),
        name = name,
        description = description)
}