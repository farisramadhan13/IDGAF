package com.capstoneproject.aplikasiantifoodwaste.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentHomeBinding
import com.capstoneproject.aplikasiantifoodwaste.profile.Users
import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanActivity
import com.capstoneproject.aplikasiantifoodwaste.scan.Storage
import com.capstoneproject.aplikasiantifoodwaste.searchfood.SearchFood
import com.capstoneproject.aplikasiantifoodwaste.searchfood.SearchFoodAdapter
import com.capstoneproject.aplikasiantifoodwaste.searchfood.SearchFoodDetailActivity
import com.capstoneproject.aplikasiantifoodwaste.searchfood.SearchFoodListActivity
import com.capstoneproject.aplikasiantifoodwaste.share.ShareActivity
import com.capstoneproject.aplikasiantifoodwaste.storage.DetailStorageActivity
import com.capstoneproject.aplikasiantifoodwaste.storage.StorageAdapter
import com.capstoneproject.aplikasiantifoodwaste.tips.TipsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var database : DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var listPenyimpananHomeRecyclerView: RecyclerView
    private lateinit var listMakananSekitarRecycleView: RecyclerView
    private lateinit var listPenyimpananHomeArrayList: ArrayList<Storage>
    private lateinit var listMakananSekitarArrayList: ArrayList<SearchFood>
    private lateinit var email: String
    private lateinit var uid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }

        listPenyimpananHomeRecyclerView = view.findViewById(R.id.horizontal_rv)
        listPenyimpananHomeRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        listPenyimpananHomeRecyclerView.setHasFixedSize(true)

        listMakananSekitarRecycleView = view.findViewById(R.id.horizontal_rv2)
        listMakananSekitarRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        listMakananSekitarRecycleView.setHasFixedSize(true)

        listPenyimpananHomeArrayList = arrayListOf<Storage>()
        listMakananSekitarArrayList = arrayListOf<SearchFood>()

        getStorageData(uid)

        binding.ivScan.setOnClickListener {
            startActivity(Intent(activity, FoodScanActivity::class.java))
        }
        binding.ivShare.setOnClickListener{
            startActivity(Intent(activity, ShareActivity::class.java))
        }
        binding.ivTips.setOnClickListener{
            startActivity(Intent(activity, TipsActivity::class.java))
        }
        binding.etSearchHome.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable) {
                hideOrShowSearchBtn()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                hideOrShowSearchBtn()
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideOrShowSearchBtn()
            }
        })
        binding.ivSearch.setOnClickListener{
            if(binding.etSearchHome.text?.trim()?.length != 0){
                startActivity(Intent(activity, SearchFoodListActivity::class.java))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun hideOrShowSearchBtn(){
        if(binding.etSearchHome.text?.trim()?.length != 0){
            binding.ivSearch.visibility = View.VISIBLE
        }
        else{
            binding.ivSearch.visibility = View.GONE
        }
    }
    private fun getStorageData(uid: String){
        database = FirebaseDatabase.getInstance().getReference("Users").child(uid)
        var userRef = uid?.let { FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Storage") }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Users::class.java)
                if (user != null) {
                    binding.tvWelcomeHome.text = "Hai ${user.name}!"
                }
            }
            override fun onCancelled(error: DatabaseError) {
//
            }
        })

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val storage = idSnapshot.getValue(Storage::class.java)
                        listPenyimpananHomeArrayList.add(storage!!)
                        //Log.e("Isi Array List", storage.toString())
                    }
                    val adapter = ListPenyimpananHomeAdapter(listPenyimpananHomeArrayList)
                    listPenyimpananHomeRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: ListPenyimpananHomeAdapter.OnItemClickCallback{
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

        database2 = FirebaseDatabase.getInstance().getReference("Share")
        database2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (shareSnapshot in snapshot.children) {
                        val food = shareSnapshot.getValue(SearchFood::class.java)
                        listMakananSekitarArrayList.add(food!!)
                    }

                    val adapter = ListMakananSekitarAdapter(listMakananSekitarArrayList)
                    listMakananSekitarRecycleView.adapter = adapter
                    adapter.setOnItemClickCallback(object : ListMakananSekitarAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: SearchFood) {
                            Intent(activity, SearchFoodDetailActivity::class.java).also {
                                it.putExtra(SearchFoodDetailActivity.Extra_Image, data.b64)
                                it.putExtra(SearchFoodDetailActivity.Extra_FoodName, data.namaMakanan)
                                it.putExtra(SearchFoodDetailActivity.Extra_Stock, data.stok)
                                it.putExtra(SearchFoodDetailActivity.Extra_Description, data.deskripsi)
                                it.putExtra(SearchFoodDetailActivity.Extra_Id, data.id)
                                it.putExtra(SearchFoodDetailActivity.Extra_Id_Makanan, data.idMakanan)
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