package com.example.job

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import android.widget.TextView
import android.widget.ImageView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val textViewDetailName: TextView = findViewById(R.id.textViewDetailName)
        val textViewDetailTitle: TextView = findViewById(R.id.textViewDetailTitle)
        val imageViewDetailPhoto: ImageView = findViewById(R.id.imageViewDetailPhoto)

        val candidateId = intent.getStringExtra("candidateId")
        if (candidateId != null) {
            val database = FirebaseDatabase.getInstance().getReference("candidates").child(candidateId)
            database.get().addOnSuccessListener { dataSnapshot ->
                val candidate = dataSnapshot.getValue(Candidate::class.java)
                if (candidate != null) {
                    textViewDetailName.text = candidate.name
                    textViewDetailTitle.text = candidate.title
                    Glide.with(this)
                        .load(candidate.photoUrl)
                        .into(imageViewDetailPhoto)
                }
            }
        }
    }
}