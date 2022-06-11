package com.capstoneproject.aplikasiantifoodwaste.order

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.aplikasiantifoodwaste.databinding.ItemPenyimpananBinding
import com.capstoneproject.aplikasiantifoodwaste.databinding.OrderCardBinding
import com.capstoneproject.aplikasiantifoodwaste.scan.Storage
import com.capstoneproject.aplikasiantifoodwaste.storage.StorageAdapter

class OrderAdapter (private val listOrder: ArrayList<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){

    private var onItemClickCallback: OrderAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OrderAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        val itemView = OrderCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }
    override fun getItemCount(): Int {
        return listOrder.size
    }
    inner class OrderViewHolder(private val binding: OrderCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order){

            val gambar = base64ToBitmap(order.gambarO)

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(order)
            }
            binding.apply {
                orderId.text = order.idOrder
                Glide.with(itemView)
                    .load(gambar)
                    .into(orderPhoto)
                orderName.text = order.namaMakananO
                orderTime.text = order.jam
                orderQuantity.text = order.jumlah
                //orderStatus.text = order.status
            }
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: Order)
    }

    private fun base64ToBitmap(b64: String?): Bitmap {
        val base64 = Base64.decode(b64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }
}