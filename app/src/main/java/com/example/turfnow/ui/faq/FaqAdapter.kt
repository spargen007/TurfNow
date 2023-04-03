package com.example.turfnow.ui.faq

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.turfnow.database.apiservice.Faq
import com.example.turfnow.databinding.FaqTextLayoutBinding

class FaqAdapter : ListAdapter<Faq, FaqAdapter.FaqViewHolder>(DiffCallback){
        class FaqViewHolder(private var binding : FaqTextLayoutBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(faq: Faq){
                binding.idNumberText.text = faq.id.toString()
                binding.QuesText.text = faq.ques
                binding.ansText.text = faq.ans
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        return FaqViewHolder(
            FaqTextLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

        override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
        companion object {
            private val DiffCallback = object : DiffUtil.ItemCallback<Faq>() {

                override fun areItemsTheSame(oldItem: Faq, newItem: Faq): Boolean {
                    return oldItem.id == newItem.id &&
                            oldItem.ques == newItem.ques &&
                            oldItem.ans == newItem.ans
                }

                override fun areContentsTheSame(oldItem: Faq, newItem: Faq): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }