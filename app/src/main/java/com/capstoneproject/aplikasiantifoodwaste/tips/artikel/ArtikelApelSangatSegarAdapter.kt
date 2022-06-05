package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.R
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
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_artikel_apel_sangat_segar, parent, false)
        val itemView = ItemArtikelApelSangatSegarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtikelApelSangatSegarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtikelApelSangatSegarViewHolder, position: Int) {
//        val currentitem = listArtikelApelSangatSegar[position]
//
//        holder.judul.text = currentitem.judul
//        holder.deskripsi.text = currentitem.deskripsi
//        holder.gambar.text = currentitem.gambar
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
                    .load(artikel.gambar)
                    .into(ivGambarListArtikelApelSangatSegar)
                tvJudulListArtikelApelSangatSegar.text = artikel.judul
                tvDeskripsiListArtikelApelSangatSegar.text = artikel.deskripsi
            }
        }
    }
//    class ArtikelApelSangatSegarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

//        val judul: TextView = itemView.findViewById(R.id.tv_judul_list_artikel_apel_sangat_segar)
//        val deskripsi: TextView = itemView.findViewById(R.id.tv_deskripsi_list_artikel_apel_sangat_segar)
//        val gambar: ImageView = itemView.findViewById(R.id.iv_gambar_list_artikel_apel_sangat_segar)
//    }
    interface OnItemClickCallback{
        fun onItemClicked(data: ArtikelApelSangatSegar)
    }
}