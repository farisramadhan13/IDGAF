package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodDetailBinding
import com.capstoneproject.aplikasiantifoodwaste.profile.AddAddressActivity

class SearchFoodDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val b64image: String? = intent.getStringExtra("EXTRA_IMAGE")
        binding.ivMakanan.setImageBitmap(base64ToBitmap(b64image))
        val foodName = intent.getStringExtra("EXTRA_FOOD_NAME")
        binding.tvNamaMakanan.text = foodName
        val stock = intent.getStringExtra("EXTRA_STOCK")
        binding.tvStok.text = stock
        val desc = intent.getStringExtra("EXTRA_DESCRIPTION")
        binding.tvDeskripsi.text = desc

        binding.btnAmbilMakananAvailable.setOnClickListener{
            startActivity(Intent(this@SearchFoodDetailActivity, TakeFoodActivity::class.java))
            finish()
        }
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}