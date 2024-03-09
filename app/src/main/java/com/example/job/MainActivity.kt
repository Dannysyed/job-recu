package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView
        val recyclerViewPosts = findViewById<RecyclerView>(R.id.recyclerViewPosts)
        recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        // You will set the adapter later once you have the data from Firebase

        // Navigate to CandidateActivity
        val fabViewCandidates = findViewById<FloatingActionButton>(R.id.fabViewCandidates)
        fabViewCandidates.setOnClickListener {
            startActivity(Intent(this, CandidateActivity::class.java))
        }
    }
}
