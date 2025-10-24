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

    // Cards
    class MainHolder(private val binding : CardActivBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activ: ActivModel, listener: ActivListener) {
            binding.activType.text = activ.type
            binding.time.text = "${activ.time.toString()} minutes"
            binding.RPE.text = "RPE: ${activ.RPE.toString()}"
            binding.note.text = activ.note

            binding.root.setOnClickListener { listener.onActivClick(activ) }
        }
    }
}
