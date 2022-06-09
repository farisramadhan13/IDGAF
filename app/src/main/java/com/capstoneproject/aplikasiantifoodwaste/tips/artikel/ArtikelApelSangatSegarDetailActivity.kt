package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityArtikelApelSangatSegarDetailBinding

class ArtikelApelSangatSegarDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtikelApelSangatSegarDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelApelSangatSegarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val gambar = intent.getStringExtra(Extra_Gambar)
        val judul = intent.getStringExtra(Extra_Judul)
        val deskripsi = intent.getStringExtra(Extra_Deskripsi)
        val sumber = intent.getStringExtra(Extra_Sumber)

        binding.apply {
            Glide.with(this@ArtikelApelSangatSegarDetailActivity)
                .load(gambar)
                .into(ivGambarDetailArtikelApelSangatSegar)
            tvJudulDetailArtikelApelSangatSegar.text = judul
            tvDeskripsiDetailArtikelApelSangatSegar.text = deskripsi
            tvSumberDetailArtikelApelSangatSegar.text = sumber
        }

    }

    companion object{
        const val Extra_Judul = "extra_judul"
        const val Extra_Deskripsi = "extra_deskripsi"
        const val Extra_Sumber = "extra_sumber"
        const val Extra_Gambar = "extra_gambar"
    }
}