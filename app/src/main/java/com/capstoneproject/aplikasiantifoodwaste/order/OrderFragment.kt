package com.capstoneproject.aplikasiantifoodwaste.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.capstoneproject.aplikasiantifoodwaste.R
import com.capstoneproject.aplikasiantifoodwaste.databinding.FragmentOrderBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OrderFragment : Fragment() {

    private lateinit var binding : FragmentOrderBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val sectionsPagerAdapter = SectionsPagerAdapter(FragmentActivity().supportFragmentManager)
//        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tab_layout)
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = resources.getString(TAB_TITLES[position])
////        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }
}