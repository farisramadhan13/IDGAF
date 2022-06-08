package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySearchFoodListBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.ActivitySplashBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.SaveFoodScanActivity
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
        supportActionBar?.hide()

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

                    var adapter = SearchFoodAdapter(searchFoodArrayList)
                    searchFoodRecyclerView.adapter = SearchFoodAdapter(searchFoodArrayList)

                    adapter.setOnItemClickListener(object: SearchFoodAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            var namaMakanan = searchFoodArrayList[position].namaMakanan
                            var stok = searchFoodArrayList[position].stok
                            var deskripsi = searchFoodArrayList[position].deskripsi
                            var b64 = searchFoodArrayList[position].b64

                            val intent = Intent(this@SearchFoodListActivity, SearchFoodDetailActivity::class.java)
                            intent.putExtra("EXTRA_IMAGE", b64 )
                            intent.putExtra("EXTRA_FOOD_NAME", namaMakanan)
                            intent.putExtra("EXTRA_STOCK", stok)
                            intent.putExtra("EXTRA_DESCRIPTION", deskripsi)
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}