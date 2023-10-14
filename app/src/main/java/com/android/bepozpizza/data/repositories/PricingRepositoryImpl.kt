package com.android.bepozpizza.data.repositories

import com.android.bepozpizza.data.dtos.OfferConfigItem
import kotlinx.coroutines.delay
import javax.inject.Inject

class PricingRepositoryImpl @Inject constructor() : PricingRepository {

    override suspend fun getPricingConfiguration(): List<OfferConfigItem> {
        delay(1000)
        return listOfPricingConfig
    }
}