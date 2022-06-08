package com.capstoneproject.aplikasiantifoodwaste.storage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.databinding.ItemArtikelApelSangatSegarBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.ItemPenyimpananBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.Storage
import com.capstoneproject.aplikasiantifoodwaste.tips.artikel.ArtikelApelSangatSegar
import com.capstoneproject.aplikasiantifoodwaste.tips.artikel.ArtikelApelSangatSegarAdapter

class StorageAdapter (private val listStorage: ArrayList<Storage>) : RecyclerView.Adapter<StorageAdapter.StorageViewHolder>(){

    private var onItemClickCallback: StorageAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: StorageAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StorageAdapter.StorageViewHolder {
        val itemView = ItemPenyimpananBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StorageViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: StorageAdapter.StorageViewHolder, position: Int) {
        holder.bind(listStorage[position])
    }
    override fun getItemCount(): Int {
        return listStorage.size
    }
    inner class StorageViewHolder(private val binding: ItemPenyimpananBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(storage: Storage){

            val gambar = base64ToBitmap(storage.gambar)

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(storage)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(gambar)
                    .into(ivPenyimpanan)
                tvNamaBahan.text = storage.namaBahan
                tvStatus.text = storage.kualitas
            }
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: Storage)
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}