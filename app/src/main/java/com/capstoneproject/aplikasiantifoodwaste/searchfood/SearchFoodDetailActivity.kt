package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodDetailBinding
import com.capstoneproject.aplikasiantifoodwaste.profile.AddAddressActivity

class SearchFoodDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnAmbilMakananAvailable.setOnClickListener{
            startActivity(Intent(this@SearchFoodDetailActivity, TakeFoodActivity::class.java))
            finish()
        }
    }
}