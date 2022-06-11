package com.capstoneproject.aplikasiantifoodwaste.order

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityAddressBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityDetailOrderBinding
import com.capstoneproject.aplikasiantifoodwaste.profile.Users
import com.capstoneproject.aplikasiantifoodwaste.storage.DetailStorageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailOrderBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var userRef: DatabaseReference
    private lateinit var email: String
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }

        val idOrder = intent.getStringExtra("EXTRA_ID_ORDER")!!
        Log.e("idorder", idOrder)

        databaseRef = FirebaseDatabase.getInstance().getReference("Order").child(idOrder)
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("order: ", snapshot.getValue(Order::class.java).toString())

                var order: Order? = snapshot.getValue(Order::class.java)
                var idPenerima = order?.idPenerima
                var idPemberi = order?.idPemberi

                binding.tvIdOrder.text = "Order ID: ${order?.idOrder}"
                binding.ivMakanan.setImageBitmap(base64ToBitmap(order?.gambarO.toString()))
                binding.tvNamaMakanan.text = order?.namaMakananO
                binding.tvStok.text = order?.jumlah
                binding.tvDeskripsi.text = order?.deskripsi

                if(uid.equals(idPenerima)){
                    binding.textDibagikanOleh.visibility = View.VISIBLE
                    binding.textDiambilOleh.visibility = View.GONE

                    userRef = idPemberi?.let {
                        FirebaseDatabase.getInstance().getReference("Users").child(it)
                    }!!

                    userRef.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.e("user: ", snapshot.getValue(Users::class.java).toString())

                            var user: Users? = snapshot.getValue(Users::class.java)
                            binding.tvGiverName.text = user?.name
                            binding.tvGiverTelp.text = user?.telp
                            binding.tvGiverAddress.text = user?.address
                            binding.ivGiver.setImageBitmap(base64ToBitmap(user?.photo))
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                    binding.tvJamAmbil.text = "Jam Ambil: ${order?.jam}"
                }
                else if(uid.equals(idPemberi)){
                    binding.textDibagikanOleh.visibility = View.GONE
                    binding.textDiambilOleh.visibility = View.VISIBLE

                    userRef = idPenerima?.let {
                        FirebaseDatabase.getInstance().getReference("Users").child(it)
                    }!!

                    userRef.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.e("user: ", snapshot.getValue(Users::class.java).toString())

                            var user: Users? = snapshot.getValue(Users::class.java)
                            binding.tvGiverName.text = user?.name
                            binding.tvGiverTelp.text = user?.telp
                            binding.tvGiverAddress.visibility = View.GONE
                            binding.ivGiver.setImageBitmap(base64ToBitmap(user?.photo))
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                    binding.tvJamAmbil.text = "Jam Ambil: ${order?.jam}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

//    companion object{
//        const val Extra_Id = "extra_id"
//        const val Extra_Gambar = "extra_gambar"
//        const val Extra_NamaMakanan = "extra_namamakanan"
//        const val Extra_Jam = "extra_jam"
//        const val Extra_Jumlah = "extra_jumlah"
//    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}