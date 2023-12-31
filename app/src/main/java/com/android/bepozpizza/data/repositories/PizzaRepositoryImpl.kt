package com.android.bepozpizza.data.repositories

import com.android.bepozpizza.base.BaseRepository
import com.android.bepozpizza.data.dtos.PizzaItem
import kotlinx.coroutines.delay
import javax.inject.Inject

class PizzaRepositoryImpl @Inject constructor() : BaseRepository(), PizzaRepository {

    override suspend fun getPizzaListing(): List<PizzaItem> {
        delay(1000)
        return listOfPizzas
    }
}