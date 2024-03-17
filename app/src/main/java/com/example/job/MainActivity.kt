package com.example.job

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var buttonSignInOrOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        buttonSignInOrOut = findViewById(R.id.buttonSignInOrOut)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        updateSignInOutButton()

        buttonSignInOrOut.setOnClickListener {
            if (mAuth.currentUser != null) {
                mAuth.signOut()
                updateSignInOutButton()
            } else {
                startActivity(Intent(this, SignIn::class.java))
            }
        }

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        // Navigates to CandidateActivity
        val fabViewCandidates = findViewById<FloatingActionButton>(R.id.fabViewCandidates)
        fabViewCandidates.setOnClickListener {
            startActivity(Intent(this, CandidateActivity::class.java))
        }
    }

    private fun updateSignInOutButton() {
        if (mAuth.currentUser != null) {
            buttonSignInOrOut.text = getString(R.string.sign_out)
        } else {
            buttonSignInOrOut.text = getString(R.string.sign_in)
        }
    }

    override fun onStart() {
        super.onStart()
        updateSignInOutButton()
    }
}
