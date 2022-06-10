package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodListBinding
import com.google.firebase.database.*

class SearchFoodListActivity : AppCompatActivity(){
    private lateinit var binding : ActivitySearchFoodListBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var searchFoodRecyclerView: RecyclerView
    private lateinit var searchFoodArrayList: ArrayList<SearchFood>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        searchFoodRecyclerView = findViewById(R.id.searchFoodList)
        searchFoodRecyclerView.layoutManager = LinearLayoutManager(this@SearchFoodListActivity)
        searchFoodRecyclerView.setHasFixedSize(true)

        searchFoodArrayList = arrayListOf<SearchFood>()
        getSearchFoodData()
    }

    private fun getSearchFoodData() {

        dbref = FirebaseDatabase.getInstance().getReference("Share")

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(shareSnapshot in snapshot.children){
                        val food = shareSnapshot.getValue(SearchFood::class.java)
                        searchFoodArrayList.add(food!!)
                    }

                    val adapter = SearchFoodAdapter(searchFoodArrayList)
                    searchFoodRecyclerView.adapter = SearchFoodAdapter(searchFoodArrayList)
                    adapter.setOnItemClickCallback(object: SearchFoodAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: SearchFood) {
                            Intent(this@SearchFoodListActivity, SearchFoodDetailActivity::class.java).also{
                                it.putExtra(SearchFoodDetailActivity.Extra_Image, data.b64 )
                                it.putExtra(SearchFoodDetailActivity.Extra_FoodName, data.namaMakanan)
                                it.putExtra(SearchFoodDetailActivity.Extra_Stock, data.stok)
                                it.putExtra(SearchFoodDetailActivity.Extra_Description, data.deskripsi)
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