package com.example.turfnow.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turfnow.database.entity.Category
import com.example.turfnow.databinding.CategoryLayoutBinding

class CategoryAdapter(private val context:Context,private val onItemClicked: (Category) -> Unit) : ListAdapter<Category,CategoryAdapter.CategoryViewHolder>(
    DiffCallback
){
    var tracker: SelectionTracker<String>? = null
    inner class CategoryViewHolder(private var binding: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category,context: Context,isActivated:Boolean=false) {
            binding.categoryText.text = category.name
            Glide.with(context).load(category.image).into(binding.categoryImage)
            binding.selectionLayer.isVisible = isActivated
        }
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): String = getItem(adapterPosition).name
                override fun inSelectionHotspot(e: MotionEvent): Boolean { return true }
            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val viewHolder = CategoryViewHolder(
            CategoryLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        tracker?.let {
            val item = getItem(position)
            holder.bind(getItem(position),context,it.isSelected(item.name))
        } ?: holder.bind(getItem(position),context)
    }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}