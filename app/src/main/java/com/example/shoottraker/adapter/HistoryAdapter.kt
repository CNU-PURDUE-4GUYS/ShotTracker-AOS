package com.example.shoottraker.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoottraker.databinding.ItemHistoryBinding
import com.example.shoottraker.model.History

class HistoryAdapter(val itemClickListener: (History) -> Unit) :
    ListAdapter<History, HistoryAdapter.HistoryViewModel>(diffUtil) {
    inner class HistoryViewModel(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            Glide
                .with(binding.targetImageView.context)
                .load(Uri.parse(history.imageUri))
                .into(binding.targetImageView)

            binding.targetImageView.clipToOutline = true

            binding.dateText.text = history.date
            binding.bulletCountText.text = history.totalBullet
            binding.averageSizeText.text = history.averageSize

            binding.root.setOnClickListener {
                itemClickListener(history)
            }
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
        val diffUtil = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }
}