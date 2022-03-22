package com.fadlurahmanf.starter_app_mvp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ItemAttachmentPdfBinding

class PdfAdapter(var list:List<PostResponse.Attachment>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding:ItemAttachmentPdfBinding

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemAttachmentPdfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var pdf = list[position]
        binding.tvFileName.text = pdf.fileName
    }

    override fun getItemCount(): Int {
        return list.size
    }
}