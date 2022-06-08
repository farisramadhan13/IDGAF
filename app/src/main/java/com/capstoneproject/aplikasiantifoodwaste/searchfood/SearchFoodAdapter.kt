package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R

class SearchFoodAdapter(private val searchFoodList: ArrayList<SearchFood>): RecyclerView.Adapter<SearchFoodAdapter.SearchFoodViewHolder>() {

    class SearchFoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val namaMakanan: TextView = itemView.findViewById(R.id.food_name)
        val deskripsi : TextView = itemView.findViewById(R.id.food_desc)
        val stok: TextView= itemView.findViewById(R.id.food_stock)
        val foto: ImageView = itemView.findViewById(R.id.food_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_food_card, parent,false)
        return SearchFoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchFoodViewHolder, position: Int) {
        val currentItem = searchFoodList[position]

        holder.namaMakanan.text = currentItem.namaMakanan
        holder.deskripsi.text = currentItem.deskripsi
        holder.stok.text = "Stok: ${currentItem.stok}"
        holder.foto.setImageBitmap(base64ToBitmap(currentItem.b64))
    }

    override fun getItemCount(): Int {
        return searchFoodList.size
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}