package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R

class SearchFoodAdapter(private val searchFoodList: ArrayList<SearchFood>): RecyclerView.Adapter<SearchFoodAdapter.SearchFoodViewHolder>() {

    class SearchFoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val namaMakanan: TextView = itemView.findViewById(R.id.food_name)
        val deskripsi : TextView = itemView.findViewById(R.id.food_desc)
        val stok: TextView= itemView.findViewById(R.id.food_stock)
        val b64: TextView = itemView.findViewById(R.id.food_b64)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_food_card, parent,false)
        return SearchFoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchFoodViewHolder, position: Int) {
        val currentItem = searchFoodList[position]

        holder.namaMakanan.text = currentItem.namaMakanan
        holder.deskripsi.text = currentItem.deskripsi
        holder.stok.text = currentItem.stok
        holder.b64.text = currentItem.b64
    }

    override fun getItemCount(): Int {
        return searchFoodList.size
    }
}