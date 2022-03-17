package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fadlurahmanf.starter_app_mvp.databinding.ItemMyNotesBinding

class MyNotesAdapter(var list:List<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding:ItemMyNotesBinding
    private lateinit var onItemClickCallBack:ItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack:ItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemMyNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var data = list[position]

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ItemClickCallBack{
        fun onItemClicked()
    }
}