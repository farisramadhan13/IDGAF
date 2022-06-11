package com.capstoneproject.aplikasiantifoodwaste.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.databinding.MakananSekitarkuBinding
import com.capstoneproject.aplikasiantifoodwaste.searchfood.SearchFood

class ListMakananSekitarAdapter(private val listMakananSekitar: ArrayList<SearchFood>) : RecyclerView.Adapter<ListMakananSekitarAdapter.ListMakananSekitarViewHolder>(){

    private var onItemClickCallback: ListMakananSekitarAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: ListMakananSekitarAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMakananSekitarAdapter.ListMakananSekitarViewHolder {
        val itemView = MakananSekitarkuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListMakananSekitarViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ListMakananSekitarAdapter.ListMakananSekitarViewHolder, position: Int) {
        holder.bind(listMakananSekitar[position])
    }
    override fun getItemCount(): Int {
        return listMakananSekitar.size
    }
    inner class ListMakananSekitarViewHolder(private val binding: MakananSekitarkuBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(searchFood: SearchFood){

            val gambar = base64ToBitmap(searchFood.b64)

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(searchFood)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(gambar)
                    .into(ivMakananSekitar)
                tvNamaMakananSekitarku.text = searchFood.namaMakanan
                tvStok.text = "Stok: ${searchFood.stok}"
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