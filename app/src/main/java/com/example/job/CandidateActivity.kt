package com.example.job

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CandidateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerViewCandidates = findViewById<RecyclerView>(R.id.recyclerViewCandidates)
        recyclerViewCandidates.layoutManager = LinearLayoutManager(this)
        val candidates = mutableListOf<Candidate>()
        val adapter = CandidateAdapter(candidates) { candidate ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("candidateId", candidate.id)
            startActivity(intent)
        }
        recyclerViewCandidates.adapter = adapter

        // Fetching candidates from Firebase
        val database = FirebaseDatabase.getInstance().getReference("candidates")
        println(database)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                candidates.clear()
                for (candidateSnapshot in snapshot.children) {
                    val candidate = candidateSnapshot.getValue(Candidate::class.java)
                    candidate?.let { candidates.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
