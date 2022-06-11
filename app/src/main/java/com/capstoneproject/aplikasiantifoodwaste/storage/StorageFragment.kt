package com.capstoneproject.aplikasiantifoodwaste.storage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentStorageBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StorageFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var storageRecyclerView: RecyclerView
    private lateinit var storageArrayList: ArrayList<Storage>
    private lateinit var email: String
    private lateinit var uid: String
    private lateinit var binding: FragmentStorageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storageRecyclerView = view.findViewById(R.id.rv_list_penyimpanan)
        storageRecyclerView.layoutManager = LinearLayoutManager(activity)
        storageRecyclerView.setHasFixedSize(true)

        var search = "-"
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }

        binding.etSearchStorage.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(binding.etSearchStorage.text?.trim()?.length != 0){
                    binding.ivSearch.visibility = View.VISIBLE
                }
                else{
                    binding.ivSearch.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if(binding.etSearchStorage.text?.trim()?.length != 0){
                    binding.ivSearch.visibility = View.VISIBLE
                }
                else{
                    binding.ivSearch.visibility = View.GONE
                }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.etSearchStorage.text?.trim()?.length != 0){
                    binding.ivSearch.visibility = View.VISIBLE
                }
                else{
                    binding.ivSearch.visibility = View.GONE
                }
            }
        })

        binding.ivSearch.setOnClickListener{
            if(binding.etSearchStorage.text?.trim()?.length != 0){
                storageArrayList = arrayListOf<Storage>()
                getStorageData(uid,binding.etSearchStorage.text.toString().lowercase())
            }
        }

        storageArrayList = arrayListOf<Storage>()
        getStorageData(uid,search)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getStorageData(uid: String, search: String){
        database = FirebaseDatabase.getInstance().getReference("Users")
        var userRef = uid?.let { database.child(it).child("Storage") }
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val storage = idSnapshot.getValue(Storage::class.java)
                        val catatan = storage?.catatan?.lowercase()
                        val namaBahan = storage?.namaBahan?.lowercase()
                        val kualitas = storage?.kualitas?.lowercase()
                        if(search == "-"){
                            storageArrayList.add(storage!!)
                        }
                        else{
                            if (catatan != null) {
                                if (namaBahan != null) {
                                    if (kualitas != null) {
                                        if(catatan.contains(search) || namaBahan.contains(search) || kualitas.contains(search)){
                                            storageArrayList.add(storage!!)
                                        }
                                    }
                                }
                            }
                        }
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

    private fun hideOrShowSearchBtn(){
        if(binding.etSearchStorage.text?.trim()?.length != 0){
            binding.ivSearch.visibility = View.VISIBLE
        }
        else{
            binding.ivSearch.visibility = View.GONE
        }
    }
}