package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import android.content.Intent
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

        val namaBuah = intent.getStringExtra("EXTRA_NAME")
        val kematangan = intent.getStringExtra("EXTRA_MATURITY")

        artikelApelSangatSegarArrayList = arrayListOf<ArtikelApelSangatSegar>()
        if (namaBuah != null) {
            if (kematangan != null) {
                getArtikelData(namaBuah,kematangan)
            }
        }
    }

    private fun getArtikelData(namaBuah: String, kematangan: String){
        dbref = FirebaseDatabase.getInstance().getReference("ArtikelBaru").child(namaBuah).child(kematangan)

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val artikel = idSnapshot.getValue(ArtikelApelSangatSegar::class.java)
                        artikelApelSangatSegarArrayList.add(artikel!!)
                    }

                    val adapter = ArtikelApelSangatSegarAdapter(artikelApelSangatSegarArrayList)
                    artikelApelSangatSegarRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: ArtikelApelSangatSegarAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: ArtikelApelSangatSegar) {
                            Intent(this@ArtikelApelSangatSegarActivity, ArtikelApelSangatSegarDetailActivity::class.java).also {
                                it.putExtra(ArtikelApelSangatSegarDetailActivity.Extra_Gambar, data.foto)
                                it.putExtra(ArtikelApelSangatSegarDetailActivity.Extra_Judul, data.judul)
                                it.putExtra(ArtikelApelSangatSegarDetailActivity.Extra_Deskripsi, data.deskripsi)
                                it.putExtra(ArtikelApelSangatSegarDetailActivity.Extra_Sumber, data.sumber)
                                startActivity(it)
                            }
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
//
            }
        })
    }
}