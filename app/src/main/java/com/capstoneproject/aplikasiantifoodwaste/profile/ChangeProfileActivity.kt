package com.capstoneproject.aplikasiantifoodwaste.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityChangeProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChangeProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeProfileBinding
    private lateinit var auth: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var email = intent.getStringExtra("EXTRA_EMAIL")
        var uid = intent.getStringExtra("EXTRA_UID")

        auth = FirebaseDatabase.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("Users/")

        var userRef = uid?.let { databaseRef.child(it) }
        if (userRef != null) {
            userRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("first_name", snapshot.getValue(Users::class.java).toString())

                    var users: Users? = snapshot.getValue(Users::class.java)
                    binding.etEditProfileName.setText(users?.name)
                    binding.etEditProfileTelephone.setText(users?.telp)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.etEditProfileEmail.setText(email)

        binding.btnEditProfileSave.setOnClickListener {
            //kirim data profile

            if(binding.etEditProfileName.text?.trim()?.length != 0 && binding.etEditProfileEmail.text?.trim()?.length != 0 && binding.etEditProfileTelephone.text?.trim()?.length != 0){
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user?.uid
                val userDetail = Users(binding.etEditProfileName.text.toString(), email, binding.etEditProfileTelephone.text.toString())

                var database = Firebase.database.reference
                if (uid != null) {
                    database.child("Users").child(uid).setValue(userDetail)
                }

                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Data belum lengkap", Toast.LENGTH_SHORT).show()
            }

        }
    }
}