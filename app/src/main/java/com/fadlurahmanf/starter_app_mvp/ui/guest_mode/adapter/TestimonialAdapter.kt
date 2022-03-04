package com.fadlurahmanf.starter_app_mvp.ui.guest_mode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.data.response.core.TestimonialResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ItemTestimonialBinding

class TestimonialAdapter(var list:ArrayList<TestimonialResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding:ItemTestimonialBinding
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var title:TextView = view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTestimonialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var testimonial = list[position]
        binding.tvQuotes.text = testimonial.quote?:""
        binding.tvName.text = testimonial.name?:""
        binding.tvLocation.text = testimonial.occupation?:""
    }

    override fun getItemCount(): Int {
        return list.size
    }
}