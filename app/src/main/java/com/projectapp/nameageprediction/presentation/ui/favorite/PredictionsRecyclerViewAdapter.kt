package com.projectapp.nameageprediction.presentation.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectapp.nameageprediction.databinding.RecyclerViewItemBinding
import com.projectapp.nameageprediction.domain.models.NameAgePrediction

class PredictionsRecyclerViewAdapter :
    ListAdapter<NameAgePrediction, PredictionsRecyclerViewAdapter.PredictionsViewHolder>(
        itemComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionsViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PredictionsViewHolder(private val predictionItemLayoutBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(predictionItemLayoutBinding.root) {

        fun bind(prediction: NameAgePrediction) {
            predictionItemLayoutBinding.nameTv.text = prediction.name
            predictionItemLayoutBinding.isFavoriteCheckbox.isChecked = false //TODO
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