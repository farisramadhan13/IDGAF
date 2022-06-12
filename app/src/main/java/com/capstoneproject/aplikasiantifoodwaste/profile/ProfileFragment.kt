package com.capstoneproject.aplikasiantifoodwaste.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.aplikasiantifoodwaste.atribut.AtributActivity
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentProfileBinding
import com.capstoneproject.aplikasiantifoodwaste.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment(){

    private lateinit var auth : FirebaseAuth
    private lateinit var auth1: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var binding : FragmentProfileBinding
    private lateinit var email: String
    private lateinit var uid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }

        binding.tvEmailProfile.text = email

        auth1 = FirebaseDatabase.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("Users/")

        var userRef = uid?.let { databaseRef.child(it) }
        if (userRef != null) {
            userRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("first_name", snapshot.getValue(Users::class.java).toString())

                    var users: Users? = snapshot.getValue(Users::class.java)
                    binding.tvNameProfile.text = users?.name
                    binding.tvTelephoneProfile.text = users?.telp
                    binding.tvAddressProfile.text = users?.address
                    binding.ivProfilePhoto.setImageBitmap(base64ToBitmap(users?.photo))
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.btnProfileLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, WelcomeActivity::class.java))
        }

        binding.buttonAtribut.setOnClickListener {
            startActivity(Intent(activity, AtributActivity::class.java))
        }

        binding.btnProfileChange.setOnClickListener{
            val intent = Intent(activity, ChangeProfileActivity::class.java)
            intent.putExtra("EXTRA_EMAIL", email)
            intent.putExtra("EXTRA_UID", uid)
            startActivity(intent)
        }

        binding.btnMyAddress.setOnClickListener{
            startActivity(Intent(activity, AddressActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}