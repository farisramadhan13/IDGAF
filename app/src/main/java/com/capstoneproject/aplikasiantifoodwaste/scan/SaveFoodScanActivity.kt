package com.capstoneproject.aplikasiantifoodwaste.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySaveFoodScanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SaveFoodScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveFoodScanBinding
    private lateinit var database: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveFoodScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val b64image: String? = intent.getStringExtra("EXTRA_IMAGE")
        binding.ivFood.setImageBitmap(base64ToBitmap(b64image))
        val name = intent.getStringExtra("EXTRA_NAME")
        binding.etName.setText(name)
        val maturity = intent.getStringExtra("EXTRA_MATURITY")
        binding.etMaturity.setText(maturity)

        binding.btnSave.setOnClickListener {
            //SIMPAN KE API

            val gambar = b64image
            //val id =
            val namaBahan = binding.etName.text.toString()
            val kualitas = binding.etMaturity.text.toString()
            val catatan = binding.etNote.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Storage")

            val user = FirebaseAuth.getInstance().currentUser
            user?.let{
                uid = user.uid
            }

            val Storage =Storage(uid, gambar, namaBahan, kualitas, catatan)
            database.child(namaBahan).setValue(Storage).addOnSuccessListener {
                Toast.makeText(this, "Berhasil simpan makanan", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }

//            Toast.makeText(this, "Berhasil simpan makanan", Toast.LENGTH_SHORT).show()
//            finish()
        }
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}