package com.capstoneproject.aplikasiantifoodwaste.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityWelcomeBinding
import com.capstoneproject.aplikasiantifoodwaste.login.LoginActivity
import com.capstoneproject.aplikasiantifoodwaste.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonLoginWelcome.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonRegisterWelcome.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}