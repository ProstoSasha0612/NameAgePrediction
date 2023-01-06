package com.projectapp.nameageprediction.presentation.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectapp.nameageprediction.databinding.RecyclerViewItemBinding
import com.projectapp.nameageprediction.domain.models.NameAgePrediction

class PredictionsRecyclerViewAdapter :
    ListAdapter<NameAgePrediction, PredictionsRecyclerViewAdapter.PredictionsViewHolder>(
        itemComparator
    ) {

    var isShowCheckboxes: Boolean = false
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionsViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnLongClickListener {
            isShowCheckboxes = !isShowCheckboxes
            false
        }
        return PredictionsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PredictionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PredictionsViewHolder(private val predictionItemLayoutBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(predictionItemLayoutBinding.root) {

        fun bind(prediction: NameAgePrediction) {
            predictionItemLayoutBinding.nameTv.text = prediction.name
            predictionItemLayoutBinding.isFavoriteCheckbox.isVisible = isShowCheckboxes
//            predictionItemLayoutBinding.isFavoriteCheckbox.isChecked = prediction.
        }
    }

    companion object {
        val itemComparator = object : DiffUtil.ItemCallback<NameAgePrediction>() {
            override fun areItemsTheSame(
                oldItem: NameAgePrediction,
                newItem: NameAgePrediction,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NameAgePrediction,
                newItem: NameAgePrediction,
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}