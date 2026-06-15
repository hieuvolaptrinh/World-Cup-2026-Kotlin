package com.example.baseapp.ui.page.top20coin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseapp.data.remote.dto.Coin
import com.example.baseapp.databinding.ItemCoinBinding

class AdapterCoin(
    private val onClick: (Coin) -> Unit = {}
) : ListAdapter<Coin, AdapterCoin.CoinViewHolder>(DIFF_CALLBACK) {

    inner class CoinViewHolder(
        private val binding: ItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin) {
            binding.tvName.text = coin.name
            binding.tvCountry.text = coin.country
            binding.tvYear.text = coin.year.toString()

            // Load image bằng Glide
            Glide.with(binding.imgCoin.context)
                .load(coin.imgCoin)
                .into(binding.imgCoin)

            binding.root.setOnClickListener {
                onClick(coin)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Coin>() {

            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem == newItem
            }
        }
    }
}