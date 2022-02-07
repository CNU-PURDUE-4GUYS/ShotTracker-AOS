package com.example.shoottraker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoottraker.databinding.ItemHistoryBinding
import com.example.shoottraker.model.HistoryModel

class HistoryAdapter : ListAdapter<HistoryModel, HistoryAdapter.HistoryViewModel>(diffUtil) {
    inner class HistoryViewModel(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryModel) {
            binding.totalSetText.text = history.totalSet
            binding.totalShotText.text = history.totalBullet
            binding.averageSizeText.text = history.averageSize
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewModel {
        return HistoryViewModel(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewModel, position: Int) {
        return holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HistoryModel>() {
            override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}