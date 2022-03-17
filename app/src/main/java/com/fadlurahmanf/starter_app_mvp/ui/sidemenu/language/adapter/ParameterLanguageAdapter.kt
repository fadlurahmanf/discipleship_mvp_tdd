package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.data.response.core.ParameterLanguageResponse

class ParameterLanguageAdapter(var list:ArrayList<ParameterLanguageResponse>, var selected:ArrayList<String>) : RecyclerView.Adapter<ParameterLanguageAdapter.ViewHolder>() {
    private lateinit var callBack: CallBack

    fun setOnItemCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var ivCheck :ImageView = view.findViewById(R.id.iv_check)
        var tvText:TextView = view.findViewById(R.id.tv_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterLanguageAdapter.ViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_checked_text, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParameterLanguageAdapter.ViewHolder, position: Int) {
        var param = list[position]

        holder.tvText.text = param.name
        holder.ivCheck.visibility = View.GONE

        if (selected.contains(param.code)){
            holder.ivCheck.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            callBack.onParameterLanguageClicked(param)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onParameterLanguageClicked(param:ParameterLanguageResponse)
    }
}