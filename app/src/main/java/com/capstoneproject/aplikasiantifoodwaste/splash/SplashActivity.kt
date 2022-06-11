package com.capstoneproject.aplikasiantifoodwaste.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySplashBinding
import com.capstoneproject.aplikasiantifoodwaste.home.HomeActivity
import com.capstoneproject.aplikasiantifoodwaste.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            auth = FirebaseAuth.getInstance()
            if(auth.currentUser != null){
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }, 3000)

    }
}