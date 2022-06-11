package com.capstoneproject.aplikasiantifoodwaste.searchfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityTakeFoodConfirmationBinding
import com.capstoneproject.aplikasiantifoodwaste.profile.Users
import com.google.firebase.database.*

class TakeFoodConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeFoodConfirmationBinding
    private lateinit var userRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeFoodConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val idMakanan = intent.getStringExtra("EXTRA_ID_MAKANAN")!!
        val jam = intent.getStringExtra("EXTRA_JAM")!!
        val idPembagi = intent.getStringExtra("EXTRA_ID_PEMBAGI")!!
        var user: Users? = null

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(idPembagi)
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(Users::class.java)
                binding.addressName.text = user?.name.toString()
                binding.addressTelp.text = user?.telp.toString()
                binding.fullAddress.text = user?.address.toString()
                Log.e("Users", snapshot.getValue(Users::class.java).toString())
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.tvConfirmDetail.text = "Silakan ambil makanan pada jam ${jam} di alamat berikut:"



    }
}