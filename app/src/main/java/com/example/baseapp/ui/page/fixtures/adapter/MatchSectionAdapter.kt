package com.example.baseapp.ui.page.fixtures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.databinding.ItemMatchSectionBinding
import com.example.baseapp.ui.model.MatchSectionUIModel

class MatchSectionAdapter :
        ListAdapter<MatchSectionUIModel, MatchSectionAdapter.ViewHolder>(DiffCallback()) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                ItemMatchSectionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        return ViewHolder(binding, viewPool)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
            private val binding: ItemMatchSectionBinding,
            private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {

        private val matchAdapter = MatchHomeAdapter()

        init {
            binding.rvSectionMatches.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = matchAdapter
                setRecycledViewPool(viewPool)
            }
        }

        fun bind(section: MatchSectionUIModel) {
            binding.tvSectionTitle.text = section.title
            matchAdapter.submitList(section.matches)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MatchSectionUIModel>() {
        override fun areItemsTheSame(
                oldItem: MatchSectionUIModel,
                newItem: MatchSectionUIModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
                oldItem: MatchSectionUIModel,
                newItem: MatchSectionUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
