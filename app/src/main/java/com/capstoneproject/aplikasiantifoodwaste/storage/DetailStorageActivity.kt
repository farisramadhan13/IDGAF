package com.capstoneproject.aplikasiantifoodwaste.storage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityDetailStorageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailStorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStorageBinding
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bahan = intent.getStringExtra(Extra_Bahan)
        val gambarBahan = intent.getStringExtra(Extra_Gambar)
        val kualitasBahan = intent.getStringExtra(Extra_Kualitas)
        val catatan = intent.getStringExtra(Extra_Catatan)
        val idStorage = intent.getStringExtra(Extra_Id)

        binding.apply {
            ivPenyimpanan.setImageBitmap(base64ToBitmap(gambarBahan))
            tvNamaBahan.text = bahan
            tvStatus.text = kualitasBahan
            tvCatatan.text = catatan
        }

        binding.remove.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                uid = user.uid
            }
            val mPostReference =
                idStorage?.let { it1 ->
                    FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Storage").child(
                        it1
                    )
                }
            if (mPostReference != null) {
                mPostReference.removeValue()
            }
            Toast.makeText(this, "Data makanan berhasil dihapus dari penyimpanan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }

    companion object{
        const val Extra_Bahan = "extra_bahan"
        const val Extra_Gambar = "extra_gambar"
        const val Extra_Kualitas = "extra_kualitas"
        const val Extra_Catatan = "extra_catatan"
        const val Extra_Id = "extra_id"
    }
}