package com.capstoneproject.aplikasiantifoodwaste.searchfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodListBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySplashBinding
import com.google.firebase.database.*

class SearchFoodListActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchFoodListBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var searchFoodRecyclerView: RecyclerView
    private lateinit var searchFoodArrayList: ArrayList<SearchFood>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchFoodRecyclerView = findViewById(R.id.searchFoodList)
        searchFoodRecyclerView.layoutManager = LinearLayoutManager(this)
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

                    searchFoodRecyclerView.adapter = SearchFoodAdapter(searchFoodArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}