package com.capstoneproject.aplikasiantifoodwaste.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityAddAddressBinding

class AddAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnAddAddress.setOnClickListener {

            Toast.makeText(this, "Alamat berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}