package com.capstoneproject.aplikasiantifoodwaste.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityAddressBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityDetailOrderBinding

class DetailOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.
    }

    companion object{
        const val Extra_Id = "extra_id"
        const val Extra_Gambar = "extra_gambar"
        const val Extra_NamaMakanan = "extra_namamakanan"
        const val Extra_Jam = "extra_jam"
        const val Extra_Jumlah = "extra_jumlah"
    }
}