package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.databinding.ItemArtikelApelSangatSegarBinding

class ArtikelApelSangatSegarAdapter(private val listArtikelApelSangatSegar: ArrayList<ArtikelApelSangatSegar>) : RecyclerView.Adapter<ArtikelApelSangatSegarAdapter.ArtikelApelSangatSegarViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtikelApelSangatSegarViewHolder {
        val itemView = ItemArtikelApelSangatSegarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtikelApelSangatSegarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtikelApelSangatSegarViewHolder, position: Int) {
        holder.bind(listArtikelApelSangatSegar[position])
    }

    override fun getItemCount(): Int {
        return listArtikelApelSangatSegar.size
    }

    inner class ArtikelApelSangatSegarViewHolder(private val binding: ItemArtikelApelSangatSegarBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(artikel: ArtikelApelSangatSegar){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(artikel)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(artikel.foto)
                    .into(ivGambarListArtikelApelSangatSegar)
                tvJudulListArtikelApelSangatSegar.text = artikel.judul
                tvDeskripsiListArtikelApelSangatSegar.text = artikel.deskripsi
            }
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: ArtikelApelSangatSegar)
    }
}