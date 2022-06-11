package com.capstoneproject.aplikasiantifoodwaste.order

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentOrderBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.Storage
import com.capstoneproject.aplikasiantifoodwaste.storage.DetailStorageActivity
import com.capstoneproject.aplikasiantifoodwaste.storage.StorageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrderFragment : Fragment() {

    private lateinit var binding : FragmentOrderBinding

    private lateinit var listAmbilRecyclerView: RecyclerView
    private lateinit var listBagiRecyclerView: RecyclerView
    private lateinit var listAmbilArrayList: ArrayList<Order>
    private lateinit var listBagiArrayList: ArrayList<Order>
    private lateinit var database: DatabaseReference
    private lateinit var email: String
    private lateinit var uid: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAmbilRecyclerView = view.findViewById(R.id.rv_ambil)
        listBagiRecyclerView = view.findViewById(R.id.rv_bagi)
        listAmbilRecyclerView.layoutManager = LinearLayoutManager(activity)
        listBagiRecyclerView.layoutManager = LinearLayoutManager(activity)
        listAmbilRecyclerView.setHasFixedSize(true)
        listBagiRecyclerView.setHasFixedSize(true)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email = user.email.toString()
            uid = user.uid
        }

        listAmbilArrayList = arrayListOf<Order>()
        listBagiArrayList = arrayListOf<Order>()

        getAmbilData(uid)
        getBagiData(uid)

        binding.tvAmbil.setOnClickListener {
            binding.tvAmbil.setTextColor(Color.parseColor("#FE6C32"))
            binding.tvBagi.setTextColor(Color.parseColor("#D3D3D3"))
            binding.rvAmbil.visibility = View.VISIBLE
            binding.rvBagi.visibility = View.GONE
        }

        binding.tvBagi.setOnClickListener {
            binding.tvAmbil.setTextColor(Color.parseColor("#D3D3D3"))
            binding.tvBagi.setTextColor(Color.parseColor("#FE6C32"))
            binding.rvAmbil.visibility = View.GONE
            binding.rvBagi.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getAmbilData(uid: String){
        database = FirebaseDatabase.getInstance().getReference("Order")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val order = idSnapshot.getValue(Order::class.java)
                        if (order != null) {
                            if(order.idPenerima.toString().equals(uid)){
                                Log.e("uid", uid)
                                Log.e("idPenerima", order.idPenerima.toString())
                                listAmbilArrayList.add(order!!)
                            }
                        }
                    }
                    val adapter = OrderAdapter(listAmbilArrayList)
                    listAmbilRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: OrderAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: Order) {
                            Intent(activity, DetailOrderActivity::class.java).also {
                                it.putExtra("EXTRA_ID_ORDER", data.idOrder )
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

    private fun getBagiData(uid: String){
        database = FirebaseDatabase.getInstance().getReference("Order")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(idSnapshot in snapshot.children){
                        val order = idSnapshot.getValue(Order::class.java)
                        if (order != null) {
                            if(order.idPemberi.toString().equals(uid)){
                                Log.e("uid", uid)
                                Log.e("idPemberi", order.idPemberi.toString())
                                listBagiArrayList.add(order!!)
                            }
                        }
                    }
                    val adapter = OrderAdapter(listBagiArrayList)
                    listBagiRecyclerView.adapter = adapter
                    adapter.setOnItemClickCallback(object: OrderAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: Order) {
                            Intent(activity, DetailOrderActivity::class.java).also {
                                it.putExtra("EXTRA_ID_ORDER", data.idOrder )
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