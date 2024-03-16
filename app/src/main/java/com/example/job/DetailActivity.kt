package com.example.job

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import android.widget.TextView
import android.widget.ImageView

class DetailActivity : AppCompatActivity() {
    private lateinit var buttonConnect: Button
    private var isConnected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val textViewDetailName: TextView = findViewById(R.id.textViewDetailName)
        val textViewDetailTitle: TextView = findViewById(R.id.textViewDetailTitle)
        val imageViewDetailPhoto: ImageView = findViewById(R.id.imageViewDetailPhoto)
        buttonConnect = findViewById(R.id.buttonConnect)

        val candidateId = intent.getStringExtra("candidateId")
        Log.d("DetailActivity", "Candidate ID: $candidateId")
        if (candidateId != null) {
            val database = FirebaseDatabase.getInstance().getReference("candidates").child(candidateId)
            Log.d("DetailActivity", "Candidate ID: $database")
            database.get().addOnSuccessListener { dataSnapshot ->
                val candidate = dataSnapshot.getValue(Candidate::class.java)
                Log.d("DetailActivity", "Candidate ID: $candidate")

                if (candidate != null) {
                    textViewDetailName.text = candidate.name
                    textViewDetailTitle.text = candidate.title
                    Glide.with(this)
                        .load(candidate.photoUrl)
                        .into(imageViewDetailPhoto)
                    isConnected = false
                    updateConnectButton()
                }
            }
        }
        buttonConnect.setOnClickListener {
            isConnected = !isConnected
            updateConnectButton()

        }

    }
    private fun updateConnectButton() {
        val buttonText = if (isConnected) "Disconnect" else "Connect"
        buttonConnect.text = buttonText
    }
}
