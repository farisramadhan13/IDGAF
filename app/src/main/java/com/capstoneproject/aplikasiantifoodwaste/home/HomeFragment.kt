package com.capstoneproject.aplikasiantifoodwaste.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentHomeBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.FoodScanActivity

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivScan.setOnClickListener {
            startActivity(Intent(activity, FoodScanActivity::class.java))
        }
        binding.ivShare.setOnClickListener{

        }
        binding.ivTips.setOnClickListener{

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}