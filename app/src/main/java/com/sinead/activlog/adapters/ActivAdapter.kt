package com.sinead.activlog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinead.activlog.databinding.CardActivBinding
import com.sinead.activlog.models.ActivModel

interface ActivListener {
    fun onActivClick(activ: ActivModel)
}

class ActivAdapter(private var activs: List<ActivModel>,
    private val listener: ActivListener) :
    RecyclerView.Adapter<ActivAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardActivBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val activ = activs[holder.adapterPosition]
        holder.bind(activ, listener)
    }

    override fun getItemCount(): Int = activs.size

    class MainHolder(private val binding : CardActivBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activ: ActivModel, listener: ActivListener) {
            binding.activType.text = activ.type
            binding.duration.text = activ.duration

            binding.root.setOnClickListener { listener.onActivClick(activ) }
        }
    }
}
