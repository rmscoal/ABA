package com.example.aba.ui.belajar.kata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aba.R
import com.example.aba.data.preferences.KataModel

class ListKataAdapter(private val listKataAdapter: ArrayList<KataModel>?) : RecyclerView.Adapter<ListKataAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_kata,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kata = listKataAdapter?.get(position)

        holder.tvKata.text = kata?.lema

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listKataAdapter!![holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listKataAdapter!!.size
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKata: TextView = itemView.findViewById(R.id.tv_kata)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: KataModel)
    }
}
