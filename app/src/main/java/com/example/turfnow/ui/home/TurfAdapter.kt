package com.example.turfnow.ui.home
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turfnow.database.entity.Turf
import com.example.turfnow.databinding.TurfListLayoutBinding

class TurfAdapter(private val context:Context,private val onItemClicked: (Turf) -> Unit) : ListAdapter<Turf,TurfAdapter.TurfViewHolder>(DiffCallback){
    class TurfViewHolder(private var binding : TurfListLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(turf: Turf,context: Context){
            binding.turfNameText.setText(turf.name)
            binding.locationText.setText(turf.location)
            binding.ratingText.setText(turf.ratings)
            Glide.with(context).load(turf.image).into(binding.turfImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurfViewHolder {
        val viewHolder = TurfViewHolder(TurfListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TurfViewHolder, position: Int) {
        holder.bind(getItem(position),context)
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Turf>() {
            override fun areItemsTheSame(oldItem: Turf, newItem: Turf): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Turf, newItem: Turf): Boolean {
                return oldItem == newItem
            }
        }
    }
}