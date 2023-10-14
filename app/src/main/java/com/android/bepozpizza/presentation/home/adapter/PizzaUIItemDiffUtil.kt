package com.android.bepozpizza.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.android.bepozpizza.domain.models.PizzaUIItem

class PizzaUIItemDiffUtil(private val oldItemList: List<PizzaUIItem>, private val newItemList: List<PizzaUIItem>) : DiffUtil.Callback() {


    override fun getOldListSize() = oldItemList.size

    override fun getNewListSize() = newItemList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemList[oldItemPosition] == newItemList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val pizzaUIItemOld = oldItemList[oldItemPosition]
        val pizzaUIItemNew = newItemList[newItemPosition]
        return pizzaUIItemOld.pizzaType == pizzaUIItemNew.pizzaType && pizzaUIItemOld.itemQty == pizzaUIItemNew.itemQty
    }
}