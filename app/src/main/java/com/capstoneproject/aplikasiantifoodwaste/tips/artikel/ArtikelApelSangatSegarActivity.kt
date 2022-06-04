package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivityArtikelApelSangatSegarBinding
import com.google.firebase.database.*

class ArtikelApelSangatSegarActivity : AppCompatActivity() {
    private lateinit var binding : ActivityArtikelApelSangatSegarBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var artikelArrayList: ArrayList<Artikel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelApelSangatSegarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.rvListArtikelApelSangatSegar.layoutManager = LinearLayoutManager(this@ArtikelApelSangatSegarActivity)
        binding.rvListArtikelApelSangatSegar.setHasFixedSize(true)

        artikelArrayList = arrayListOf<Artikel>()
        getUserData()
    }

    private fun getUserData(){
        dbref = FirebaseDatabase.getInstance().getReference("Artikel")
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val artikel = idSnapshot.getValue(Artikel::class.java)
                        artikelArrayList.add(artikel!!)
                    }
                    binding.rvListArtikelApelSangatSegar.adapter = ArtikelApelSangatSegarAdapter(artikelArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //
            }

        })
    }
}