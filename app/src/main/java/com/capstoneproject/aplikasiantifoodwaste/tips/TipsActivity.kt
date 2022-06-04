package com.capstoneproject.aplikasiantifoodwaste.tips

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTipsBinding
import com.capstoneproject.aplikasiantifoodwaste.tips.kualitas.ApelActivity

class TipsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.gambarApelTips.setOnClickListener {
            startActivity(Intent(this, ApelActivity::class.java))
        }
        binding.gambarPisangTips.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.gambarJerukTips.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.gambarTomatTips.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.gambarWortelTips.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.gambarSayurHijauTips.setOnClickListener {
            Toast.makeText(this,"Fitur belum dibuat", Toast.LENGTH_SHORT).show()
        }
    }
}