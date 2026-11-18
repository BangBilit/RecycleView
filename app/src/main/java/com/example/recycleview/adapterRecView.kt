package com.example.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.example.recycleview.databinding.ItemRecyclerBinding

class AdapterRecView(private val listWayang: ArrayList<DcWayang>) :
    RecyclerView.Adapter<AdapterRecView.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DcWayang)
    }

    class ListViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listWayang.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val wayang = listWayang[position]
        holder.binding.namaWayang.text = wayang.nama
        holder.binding.karakterWayang.text = wayang.karakter
        holder.binding.deskripsiWayang.text = wayang.deskripsi

        val cilcuralProgressDrawable = CircularProgressDrawable(holder.itemView.context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

        holder.binding.imageWayang.load(wayang.foto) {
            crossfade(true)
            placeholder(cilcuralProgressDrawable)
            error(R.drawable.ic_launcher_background)
        }

        holder.binding.imageWayang.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Gambar ${wayang.nama} diklik",
                Toast.LENGTH_SHORT).show()

            if(::onItemClickCallback.isInitialized) {
                onItemClickCallback.onItemClicked(wayang)
            }
        }
    }
}
