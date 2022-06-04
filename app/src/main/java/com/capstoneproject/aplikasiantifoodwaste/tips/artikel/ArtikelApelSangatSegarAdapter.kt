package com.capstoneproject.aplikasiantifoodwaste.tips.artikel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.aplikasiantifoodwaste.R

class ArtikelApelSangatSegarAdapter(private val listArtikel: ArrayList<Artikel>) : RecyclerView.Adapter<ArtikelApelSangatSegarAdapter.ArtikelApelSangatSegarViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtikelApelSangatSegarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_artikel_apel_sangat_segar, parent, false)
        return ArtikelApelSangatSegarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtikelApelSangatSegarViewHolder, position: Int) {
        val currentitem = listArtikel[position]

        holder.judul.text = currentitem.judul
        holder.desc.text = currentitem.deskripsi

    }

    override fun getItemCount(): Int {
        return listArtikel.size
    }
    class ArtikelApelSangatSegarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val judul: TextView = itemView.findViewById(R.id.tv_judul_list_artikel_apel_sangat_segar)
        val desc: TextView = itemView.findViewById(R.id.tv_deskripsi_list_artikel_apel_sangat_segar)
    }
}