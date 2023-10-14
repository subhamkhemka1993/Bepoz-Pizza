package com.android.bepozpizza.di

import com.android.bepozpizza.data.PreferenceStorage
import com.android.bepozpizza.data.SessionManager
import com.android.bepozpizza.data.repositories.PizzaRepository
import com.android.bepozpizza.data.repositories.PizzaRepositoryImpl
import com.android.bepozpizza.data.repositories.PricingRepository
import com.android.bepozpizza.data.repositories.PricingRepositoryImpl
import com.android.bepozpizza.domain.CheckoutManager
import com.android.bepozpizza.domain.CheckoutManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class BindingInjectionModule {

    @Singleton
    @Binds
    abstract fun bindSessionManagerToStorage(sessionManager: SessionManager): PreferenceStorage

    @Binds
    abstract fun bindPizzaRepositoryToPizzaRepositoryImpl(pizzaRepositoryImpl: PizzaRepositoryImpl): PizzaRepository

    @Binds
    abstract fun bindPricingRepositoryToPricingRepositoryImpl(pricingRepositoryImpl: PricingRepositoryImpl): PricingRepository

    @Binds
    abstract fun bindCheckoutManagerToCheckoutManagerImpl(checkoutManagerImpl: CheckoutManagerImpl): CheckoutManager

}