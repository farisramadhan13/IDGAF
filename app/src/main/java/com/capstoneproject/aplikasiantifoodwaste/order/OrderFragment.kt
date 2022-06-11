package com.capstoneproject.aplikasiantifoodwaste.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentOrderBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DatabaseReference

class OrderFragment : Fragment() {

    private lateinit var binding : FragmentOrderBinding
    private lateinit var listAmbilRecyclerView: RecyclerView
    private lateinit var listBagiRecyclerView: RecyclerView
    private lateinit var listAmbilArrayList: ArrayList<Order>
    private lateinit var listBagiArrayList: ArrayList<Order>
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvAmbil.setOnClickListener {
            //binding.tvAmbil.textColors
            binding.rvAmbil.visibility = View.VISIBLE
            binding.rvBagi.visibility = View.GONE
        }

        binding.tvBagi.setOnClickListener {
            //binding.tvBagi.textColors
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
}