package com.capstoneproject.aplikasiantifoodwaste.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySaveFoodScanBinding


class SaveFoodScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveFoodScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveFoodScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var b64image: String? = intent.getStringExtra("EXTRA_IMAGE")
        binding.ivFood.setImageBitmap(base64ToBitmap(b64image))
        var name = intent.getStringExtra("EXTRA_NAME")
        binding.etName.setText(name)
        var maturity = intent.getStringExtra("EXTRA_MATURITY")
        binding.etMaturity.setText(maturity)
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        var base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}