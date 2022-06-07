package com.example.aba.ui.latihan.menyusunhuruf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aba.R

class DetailSusunHurufAdapter(private val listHuruf: ArrayList<Char>?) : RecyclerView.Adapter<DetailSusunHurufAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_huruf,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val huruf = listHuruf?.get(position)

        holder.tvHuruf.setText(huruf.toString())

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listHuruf!![holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listHuruf!!.size
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHuruf: TextView = itemView.findViewById(R.id.tv_huruf)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Char)
    }
}
