package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R

import com.google.firebase.database.*

class ArtikelApelSangatSegarActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var artikelApelSangatSegarRecyclerView: RecyclerView
    private lateinit var artikelApelSangatSegarArrayList: ArrayList<ArtikelApelSangatSegar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel_apel_sangat_segar)
        supportActionBar?.hide()

        artikelApelSangatSegarRecyclerView = findViewById(R.id.rv_list_artikel_apel_sangat_segar)
        artikelApelSangatSegarRecyclerView.layoutManager = LinearLayoutManager(this)
        artikelApelSangatSegarRecyclerView.setHasFixedSize(true)

        artikelApelSangatSegarArrayList = arrayListOf<ArtikelApelSangatSegar>()
        getUserData()
    }

    private fun getUserData(){
        dbref = FirebaseDatabase.getInstance().getReference("Artikel")
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val artikel = idSnapshot.getValue(ArtikelApelSangatSegar::class.java)
                        artikelApelSangatSegarArrayList.add(artikel!!)
                    }
                    artikelApelSangatSegarRecyclerView.adapter = ArtikelApelSangatSegarAdapter(artikelApelSangatSegarArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
//
            }
        })
    }
}