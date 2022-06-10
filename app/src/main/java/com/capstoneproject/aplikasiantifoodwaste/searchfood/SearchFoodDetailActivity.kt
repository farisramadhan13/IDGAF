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

        val b64image = intent.getStringExtra(Extra_Image)
        val foodName = intent.getStringExtra(Extra_FoodName)
        val stock = intent.getStringExtra(Extra_Stock)
        val desc = intent.getStringExtra(Extra_Description)


        binding.apply {
            ivMakanan.setImageBitmap(base64ToBitmap(b64image))
            tvNamaMakanan.text = foodName
            tvStok.text = stock
            tvDeskripsi.text = desc
        }


//        binding.btnAmbilMakananAvailable.setOnClickListener{
//            startActivity(Intent(this@SearchFoodDetailActivity, TakeFoodActivity::class.java))
//            finish()
//        }
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }

    companion object {
        const val Extra_Image = "extra_image"
        const val Extra_FoodName = "extra_foodname"
        const val Extra_Stock = "extra_stock"
        const val Extra_Description = "extra_description"
    }
}