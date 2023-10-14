package com.android.bepozpizza.data.repositories

import com.android.bepozpizza.data.dtos.PizzaItem
import com.android.bepozpizza.data.dtos.PizzaType

interface PizzaRepository {

    suspend fun getPizzaListing(): List<PizzaItem>

}

val listOfPizzas = listOf(
    PizzaItem(pizzaType = PizzaType.SMALL, price = 11.99f, name = "Small Pizza", description = "10\" pizza for one person"),
    PizzaItem(pizzaType = PizzaType.MEDIUM, price = 15.99f, name = "Medium Pizza", description = "12\" Pizza for two persons"),
    PizzaItem(pizzaType = PizzaType.LARGE, price = 21.99f, name = "Large Pizza", description = "15\" Pizza for four persons"),
)