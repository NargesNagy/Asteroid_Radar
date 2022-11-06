package com.example.asteroidradar.main

import androidx.recyclerview.widget.DiffUtil

class MainDiffUtils <Item : ADiffUtil> : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.getUniqueIdentifier() == newItem.getUniqueIdentifier()


    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.getContent() == newItem.getContent()

    companion object {
        fun <Item : ADiffUtil> getInstance(): MainDiffUtils<Item> {
            return MainDiffUtils()
        }

    }
}