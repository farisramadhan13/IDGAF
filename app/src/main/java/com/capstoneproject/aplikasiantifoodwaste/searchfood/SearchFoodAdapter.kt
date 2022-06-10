package com.capstoneproject.aplikasiantifoodwaste.searchfood

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.databinding.SearchFoodCardBinding

class SearchFoodAdapter (private val listSearchFood: ArrayList<SearchFood>) : RecyclerView.Adapter<SearchFoodAdapter.SearchFoodViewHolder>(){

    private var onItemClickCallback: SearchFoodAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: SearchFoodAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchFoodAdapter.SearchFoodViewHolder {
        val itemView = SearchFoodCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchFoodViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: SearchFoodAdapter.SearchFoodViewHolder, position: Int) {
        holder.bind(listSearchFood[position])
    }
    override fun getItemCount(): Int {
        return listSearchFood.size
    }
    inner class SearchFoodViewHolder(private val binding: SearchFoodCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(searchFood: SearchFood){

            val gambar = base64ToBitmap(searchFood.b64)

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(searchFood)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(gambar)
                    .into(foodPhoto)
                foodName.text = searchFood.namaMakanan
                foodStock.text = "Stok: ${searchFood.stok}"
                foodDesc.text = searchFood.deskripsi
            }
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: SearchFood)
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}