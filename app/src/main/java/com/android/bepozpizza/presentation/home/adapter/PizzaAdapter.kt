package com.android.bepozpizza.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.bepozpizza.domain.models.PizzaUIItem
import com.android.bepozpizza.databinding.ItemLayoutPizzaBinding

class PizzaAdapter(private val itemClickCallBack: (Int, Boolean) -> Unit) : RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder>() {

    private val itemsList = arrayListOf<PizzaUIItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        return PizzaViewHolder(ItemLayoutPizzaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        val pizzaUIItem = itemsList[position]
        holder.setData(pizzaUIItem)
    }

    fun setData(pizzaUIItems: List<PizzaUIItem>) {
        val pizzaUIItemDiffUtil = PizzaUIItemDiffUtil(itemsList, pizzaUIItems)
        val calculateDiff = DiffUtil.calculateDiff(pizzaUIItemDiffUtil)
        itemsList.clear()
        itemsList.addAll(pizzaUIItems)
        calculateDiff.dispatchUpdatesTo(this)
    }

    inner class PizzaViewHolder(private val itemLayoutPizzaBinding: ItemLayoutPizzaBinding) : RecyclerView.ViewHolder(itemLayoutPizzaBinding.root) {

        init {
            itemLayoutPizzaBinding.ivPlus.setOnClickListener {
                itemClickCallBack(adapterPosition, true)
            }
            itemLayoutPizzaBinding.ivMinus.setOnClickListener {
                itemClickCallBack(adapterPosition, false)
            }
            itemLayoutPizzaBinding.tvAddToCart.setOnClickListener {
                itemClickCallBack(adapterPosition, true)
            }
        }

        fun setData(pizzaUIItem: PizzaUIItem) {
            itemLayoutPizzaBinding.apply {
                tvProductName.text = pizzaUIItem.name
                tvProductDescription.text = pizzaUIItem.description
                tvProductPrice.text = pizzaUIItem.displayPrice
                ivProduct.setImageResource(pizzaUIItem.imgRes)
                if (pizzaUIItem.itemQty > 0) {
                    tvItemCount.text = "${pizzaUIItem.itemQty}"
                    clItemCount.isVisible = true
                    tvAddToCart.isVisible = false
                } else {
                    clItemCount.isInvisible = true
                    tvAddToCart.isVisible = true
                }
            }
        }

    }
}