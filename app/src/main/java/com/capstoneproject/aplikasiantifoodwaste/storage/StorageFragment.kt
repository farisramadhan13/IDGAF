package com.capstoneproject.aplikasiantifoodwaste.storage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentStorageBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.Storage
import com.capstoneproject.aplikasiantifoodwaste.tips.artikel.ArtikelApelSangatSegar
import com.capstoneproject.aplikasiantifoodwaste.tips.artikel.ArtikelApelSangatSegarAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StorageFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var storageRecyclerView: RecyclerView
    private lateinit var storageArrayList: ArrayList<Storage>
    private lateinit var email: String
    private lateinit var uid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storageRecyclerView = view.findViewById(R.id.rv_list_penyimpanan)
        storageRecyclerView.layoutManager = LinearLayoutManager(activity)
        storageRecyclerView.setHasFixedSize(true)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }

        storageArrayList = arrayListOf<Storage>()
        getStorageData(uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    private fun getStorageData(uid: String){
        database = FirebaseDatabase.getInstance().getReference("Users")
        var userRef = uid?.let { database.child(it).child("Storage") }
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val storage = idSnapshot.getValue(Storage::class.java)
                        storageArrayList.add(storage!!)
                    }
                    val adapter = StorageAdapter(storageArrayList)
                    storageRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: StorageAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: Storage) {
                            Intent(activity, DetailStorageActivity::class.java).also {
                                it.putExtra(DetailStorageActivity.Extra_Gambar, data.gambar)
                                it.putExtra(DetailStorageActivity.Extra_Bahan, data.namaBahan)
                                it.putExtra(DetailStorageActivity.Extra_Kualitas, data.kualitas)
                                it.putExtra(DetailStorageActivity.Extra_Catatan, data.catatan)
                                it.putExtra(DetailStorageActivity.Extra_Id, data.id)
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