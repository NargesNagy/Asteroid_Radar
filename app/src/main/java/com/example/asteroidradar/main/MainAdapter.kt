package com.example.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.databinding.RecyclevItemBinding
import com.example.asteroidradar.models.Asteroid

class MainAdapter(val onClickListener: OnItemClicked) :
    ListAdapter<Asteroid, MainAdapter.MainViewHolder>(MainDiffUtils.getInstance()) {

    override fun submitList(list: List<Asteroid>?) {
        super.submitList(list)
    }

    override fun getItemCount(): Int {
        val size= super.getItemCount()
        return size
    }

    inner class MainViewHolder(private val binding: RecyclevItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.rvItem.setOnClickListener {
                onClickListener.onItemClicked(asteroid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        Log.e("TAG", "onCreateViewHolder: ")
        return MainViewHolder(
            RecyclevItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))


    }
}
