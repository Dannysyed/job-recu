package com.example.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CandidateAdapter(
    private val candidates: List<Candidate>,
    private val onClick: (Candidate) -> Unit
) : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.bind(candidate)
    }

    override fun getItemCount(): Int = candidates.size

    class CandidateViewHolder(itemView: View, val onClick: (Candidate) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val photoImageView: ImageView = itemView.findViewById(R.id.imageViewPhoto)

        fun bind(candidate: Candidate) {
            nameTextView.text = candidate.name
            titleTextView.text = candidate.title
            Glide.with(itemView.context)
                .load(candidate.photoUrl)
                .into(photoImageView)

            itemView.setOnClickListener {
                onClick(candidate)
            }
        }
    }
}
