package com.example.shoottraker.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoottraker.databinding.ItemImageDetailBinding
import com.example.shoottraker.model.Reference

class ReferenceAdapter(val itemClickListener: (Reference) -> Unit) :
    ListAdapter<Reference, ReferenceAdapter.ReferenceViewHolder>(diffUtil) {
    inner class ReferenceViewHolder(private val binding: ItemImageDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reference: Reference) {
            Glide
                .with(binding.targetImageView.context)
                .load(Uri.parse(reference.refUri))
                .into(binding.targetImageView)

            binding.targetImageView.clipToOutline = true

            binding.root.setOnClickListener {
                itemClickListener(reference)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceViewHolder {
        return ReferenceViewHolder(
            ItemImageDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReferenceViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Reference>() {
            override fun areItemsTheSame(oldItem: Reference, newItem: Reference): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Reference, newItem: Reference): Boolean {
                return oldItem == newItem
            }

        }
    }


}