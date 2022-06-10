package com.capstoneproject.aplikasiantifoodwaste.tips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityUploadArtikelBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadArtikelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadArtikelBinding
    private lateinit var artikelRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        artikelRef = FirebaseDatabase.getInstance().getReference("ArtikelBaru")

        binding.btnUploadArtikel.setOnClickListener {
            val namaBuah = binding.tiNamaBuah.text.toString()
            val kematangan = binding.tiKematangan.text.toString()
            val id = binding.tiIdArtikel.text.toString()
            val judul = binding.tiJudulArtikel.text.toString()
            val foto = binding.tiFotoArtikel.text.toString()
            val deskripsi = binding.tiDeskripsiArtikel.text.toString()
            val sumber = binding.tiSumberArtikel.text.toString()

            val artikel = Artikel(deskripsi, foto, judul, sumber)
            artikelRef.child(namaBuah).child(kematangan).child(id).setValue(artikel).addOnSuccessListener {
                Toast.makeText(this, "Artikel berhasil diupload", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }

    }
}