package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SearchFoodListActivity : AppCompatActivity(){
    private lateinit var binding : ActivitySearchFoodListBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var searchFoodRecyclerView: RecyclerView
    private lateinit var searchFoodArrayList: ArrayList<SearchFood>
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val search = intent.getStringExtra("EXTRA_SEARCH")?.lowercase()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { uid = user.uid }

        searchFoodRecyclerView = findViewById(R.id.searchFoodList)
        searchFoodRecyclerView.layoutManager = LinearLayoutManager(this@SearchFoodListActivity)
        searchFoodRecyclerView.setHasFixedSize(true)

        searchFoodArrayList = arrayListOf<SearchFood>()
        if (search != null) {
            getSearchFoodData(search,uid)
            binding.etSearchFoodList.addTextChangedListener (object : TextWatcher {
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

            binding.ivSearch.setOnClickListener {
                if(binding.etSearchFoodList.text?.trim()?.length != 0){
                    searchFoodArrayList = arrayListOf<SearchFood>()
                    getSearchFoodData(binding.etSearchFoodList.text.toString(),uid)
                }
            }
        }
        else{
            getShareFoodData(uid)
            binding.ivNotification.visibility = View.GONE
            binding.titleBarMakananYangKubagi.visibility = View.VISIBLE
            binding.etSearchFoodList.visibility = View.GONE
            binding.ivSearch.visibility = View.GONE
        }
    }

    private fun getSearchFoodData(search: String, uid: String) {

        dbref = FirebaseDatabase.getInstance().getReference("Share")

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(shareSnapshot in snapshot.children){
                        val food = shareSnapshot.getValue(SearchFood::class.java)
                        val foodName = food?.namaMakanan?.lowercase()
                        val foodDesc = food?.deskripsi?.lowercase()

                        //Buat cari hasil searchnya
                        if(!food?.id.equals(uid)){
                            if(search == "-"){
                                searchFoodArrayList.add(food!!)
                            }
                            else{
                                if (foodName != null) {
                                    if (foodDesc != null) {
                                        if(foodName.contains(search) || foodDesc.contains(search)){
                                            searchFoodArrayList.add(food!!)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    val adapter = SearchFoodAdapter(searchFoodArrayList)
                    searchFoodRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: SearchFoodAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: SearchFood) {
                            Intent(this@SearchFoodListActivity, SearchFoodDetailActivity::class.java).also{
                                it.putExtra(SearchFoodDetailActivity.Extra_Image, data.b64 )
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

    private fun getShareFoodData(uid: String){
        dbref = FirebaseDatabase.getInstance().getReference("Share")

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (shareSnapshot in snapshot.children) {
                        val food = shareSnapshot.getValue(SearchFood::class.java)

                        //Buat cari hasil searchnya
                        if (food?.id.equals(uid)) {
                            searchFoodArrayList.add(food!!)
                        }
                    }
                    val adapter = SearchFoodAdapter(searchFoodArrayList)
                    searchFoodRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: SearchFoodAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: SearchFood) {
                            Intent(this@SearchFoodListActivity, SearchFoodDetailActivity::class.java).also{
                                it.putExtra(SearchFoodDetailActivity.Extra_Image, data.b64 )
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

    private fun hideOrShowSearchBtn(){
        if(binding.etSearchFoodList.text?.trim()?.length != 0){
            binding.ivSearch.visibility = View.VISIBLE
        }
        else{
            binding.ivSearch.visibility = View.GONE
        }
    }
}