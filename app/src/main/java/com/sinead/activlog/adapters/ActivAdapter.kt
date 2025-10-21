package com.sinead.activlog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinead.activlog.databinding.CardActivBinding
import com.sinead.activlog.models.ActivModel

class ActivAdapter constructor(private var activs: List<ActivModel>) :
    RecyclerView.Adapter<ActivAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardActivBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val activ = activs[holder.adapterPosition]
        holder.bind(activ)
    }

    override fun getItemCount(): Int = activs.size

    class MainHolder(private val binding : CardActivBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activ: ActivModel) {
            binding.activType.text = activ.type
            binding.duration.text = activ.duration
        }
    }
}