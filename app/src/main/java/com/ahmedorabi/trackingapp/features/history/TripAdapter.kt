package com.ahmedorabi.trackingapp.features.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.databinding.TripItemBinding
import com.ahmedorabi.trackingapp.features.getTimeFormatted

class TripAdapter(private val callback: (trip: TripEntity) -> Unit) :
    ListAdapter<TripEntity, TripAdapter.MyViewHolder>(
        DiffCallback
    ) {


    companion object DiffCallback : DiffUtil.ItemCallback<TripEntity>() {
        override fun areItemsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TripEntity,
            newItem: TripEntity
        ): Boolean {
            return oldItem.steps == newItem.steps
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            TripItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.cardViewItem.setOnClickListener {
            callback.invoke(item)
        }

    }


    class MyViewHolder(var binding: TripItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TripEntity) {

            binding.idTv.text = item.id.toString()
            binding.stepsTv.text = item.steps

            binding.distanceTv.text = item.distance
            binding.timeTv.text = item.time.getTimeFormatted()

        }



    }




}