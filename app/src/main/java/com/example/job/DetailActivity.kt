package com.example.job
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class DetailActivity : AppCompatActivity() {
    private lateinit var buttonConnect: Button
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val textViewDetailName: TextView = findViewById(R.id.textViewDetailName)
        val textViewDetailBio: TextView = findViewById(R.id.textViewDetailBio)
        val textViewDetailTitle: TextView = findViewById(R.id.textViewDetailTitle)
        val imageViewDetailPhoto: ImageView = findViewById(R.id.imageViewDetailPhoto)
        buttonConnect = findViewById(R.id.buttonConnect)

        val candidateId = intent.getStringExtra("candidateId")
        Log.d("DetailActivity", "Candidate ID: $candidateId")
        if (candidateId != null) {
            val database = FirebaseDatabase.getInstance().getReference("candidates").child(candidateId)
            Log.d("DetailActivity", "Database Reference: $database")
            database.get().addOnSuccessListener { dataSnapshot ->
                val candidate = dataSnapshot.getValue(Candidate::class.java)
                Log.d("DetailActivity", "Candidate: $candidate")

                val isConnectedValue = dataSnapshot.child("isConnected").getValue(Boolean::class.java) ?: false
                Log.d("DetailActivity", "Fetched candidate: $candidate")
                Log.d("DetailActivity", "isConnected from database: $isConnectedValue")
                if (candidate != null) {
                    textViewDetailName.text = candidate.name
                    textViewDetailTitle.text = candidate.title
                    textViewDetailBio.text = candidate.bio
                    isConnected = isConnectedValue
                    Log.d("DetailActivity", "isConnected: $isConnected")
                    updateConnectButton()
                    val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(candidate.photoUrl)
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        Glide.with(this)
                            .load(uri)
                            .into(imageViewDetailPhoto)
                    }.addOnFailureListener { exception ->
                        Log.e("DetailActivity", "Error loading image", exception)
                    }

                }
            }.addOnFailureListener { exception ->
                Log.e("DetailActivity", "Error fetching candidate data", exception)
            }
        }

        buttonConnect.setOnClickListener {
            isConnected = !isConnected
            updateConnectButton()

            // Updating here the connection status in the database
            if (candidateId != null) {
                val database = FirebaseDatabase.getInstance().getReference("candidates").child(candidateId)
                database.child("isConnected").setValue(isConnected).addOnFailureListener { exception ->
                    Log.e("DetailActivity", "Error updating connection status", exception)
                }
            }
        }
    }

    private fun updateConnectButton() {
        val buttonText = if (isConnected) "Disconnect" else "Connect"
        buttonConnect.text = buttonText
    }
}
