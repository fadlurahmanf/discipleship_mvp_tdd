package com.fadlurahmanf.starter_app_mvp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fadlurahmanf.starter_app_mvp.core.extension.formatDate
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ItemStudygroupActiveBinding
import com.fadlurahmanf.starter_app_mvp.databinding.ItemStudygroupCanceledBinding
import com.fadlurahmanf.starter_app_mvp.databinding.ItemStudygroupWaitlistBinding

class MyGroupAdapter(var list:ArrayList<MyGroupResponse>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var bindingActive:ItemStudygroupActiveBinding
    private lateinit var bindingWaitlist:ItemStudygroupWaitlistBinding
    private lateinit var bindingCanceled:ItemStudygroupCanceledBinding
    private lateinit var context: Context

    private lateinit var studyGroupCallBack: StudyGroupCallBack

    fun setOnItemClickCallback(studyGroupCallBack: StudyGroupCallBack){
        this.studyGroupCallBack = studyGroupCallBack
    }

    inner class ActiveViewHolder(view:View):RecyclerView.ViewHolder(view){

    }

    inner class CanceledViewHolder(view:View):RecyclerView.ViewHolder(view){}

    inner class AvailableViewHolder(view: View):RecyclerView.ViewHolder(view){}

    inner class WaitlistViewHolder(view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            0 -> {
                bindingWaitlist = ItemStudygroupWaitlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                WaitlistViewHolder(bindingWaitlist.root)
            }
            1 -> {
                bindingCanceled = ItemStudygroupCanceledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CanceledViewHolder(bindingCanceled.root)
            }
            else -> {
                bindingActive = ItemStudygroupActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ActiveViewHolder(bindingActive.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var studyGroup = list[position]

        if (holder.itemViewType == 0){
//            var holder = holder as WaitlistViewHolder
        }else if (holder.itemViewType == 1){
//            var holder = holder as CanceledViewHolder
            bindingCanceled.tvWeeklyMeeting.text = studyGroup.study?.weeklyMeetingTime?:""
//            holder.tvInfoChangeStudyGroup.text = context.getString(R.string.info_allowed_change_study_group, studyGroup.changeAllowedDeadline?.formatDate()?:"")
//            holder.tvIdStudyGroup.text = studyGroup.code?:""
        }else{
//            var holder = holder as ActiveViewHolder
            bindingActive.tvWeeklyMeeting.text = studyGroup.study?.weeklyMeetingTime?:""
            bindingActive.tvInfoChangeStudygroup.text = studyGroup.changeAllowedDeadline?.formatDate()
            holder.itemView.setOnClickListener {
                studyGroupCallBack.onActiveStudyGroupCallBack(studyGroup)
            }
//            holder.tvWeeklyMeetingTime.text = studyGroup.study?.weeklyMeetingTime?:""
//            holder.tvInfoChangeStudyGroup.text = context.getString(R.string.info_allowed_change_study_group, studyGroup.changeAllowedDeadline?.formatDate()?:"")
//            holder.tvIdStudyGroup.text = studyGroup.code?:""
//            holder.itemView.setOnClickListener {
//                activeStudyGroupClickCallback.onItemClicked(studyGroup)
//            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].status) {
            "waitlist" -> {
                0
            }
            "canceled" -> {
                1
            }
            else -> {
                2
            }
        }
    }

    interface StudyGroupCallBack{
        fun onActiveStudyGroupCallBack(group:MyGroupResponse?)
    }
}