package com.fadlurahmanf.starter_app_mvp.ui.study_group

import android.os.Bundle
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.core.extension.formatDate
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityCreateStudyGroupDetailBinding
import com.fadlurahmanf.starter_app_mvp.di.component.StudyGroupComponent
import com.fadlurahmanf.starter_app_mvp.ui.study_group.widget.DateTimePickerBottomSheet
import com.fadlurahmanf.starter_app_mvp.ui.study_group.widget.NumberPickerBottomSheet
import java.util.*

class CreateStudyGroupDetailActivity : BaseActivity<ActivityCreateStudyGroupDetailBinding>(ActivityCreateStudyGroupDetailBinding::inflate) {
    private lateinit var component:StudyGroupComponent
    override fun injectView() {
        component = appComponent.studyGroupComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(isFullScreen = true, isLight = true, color = R.color.teal_200)
        initAction()
    }

    private var studyGroupCapacity:Int = 6
    private var orientationCalendar:Calendar ?= null
    private var beginsDateCalendar:Calendar ?= null

    private fun initAction() {
        binding?.tvStudyGroupCapacity?.setOnClickListener {
            var bottomSheet = NumberPickerBottomSheet()
            bottomSheet.setCallBack(object : NumberPickerBottomSheet.CallBack{
                override fun onSaveClicked(value: Int) {
                    studyGroupCapacity = value
                    binding?.tvStudyGroupCapacity?.text = studyGroupCapacity.toString()
                    bottomSheet.dismiss()
                }
            })
            var bundle = Bundle()
            bundle.putInt(NumberPickerBottomSheet.OLD_VALUE, studyGroupCapacity)
            bottomSheet.arguments = bundle
            bottomSheet.show(supportFragmentManager, NumberPickerBottomSheet::class.java.simpleName)
        }

        binding?.tvOrientationDate?.setOnClickListener {
            val bottomSheet = DateTimePickerBottomSheet()
            if (orientationCalendar != null){
                val bundle = Bundle()
                bundle.putInt(DateTimePickerBottomSheet.OLD_YEAR, orientationCalendar!!.get(Calendar.YEAR))
                bundle.putInt(DateTimePickerBottomSheet.OLD_MONTH, orientationCalendar!!.get(Calendar.MONTH))
                bundle.putInt(DateTimePickerBottomSheet.OLD_DAY_OF_MONTH, orientationCalendar!!.get(Calendar.DAY_OF_MONTH))
                bundle.putInt(DateTimePickerBottomSheet.OLD_HOUR, orientationCalendar!!.get(Calendar.HOUR_OF_DAY))
                bundle.putInt(DateTimePickerBottomSheet.OLD_MINUTE, orientationCalendar!!.get(Calendar.MINUTE))
                bottomSheet.arguments = bundle
            }
            bottomSheet.setCallBack(object : DateTimePickerBottomSheet.CallBack{
                override fun onSaveDateTimeClicked(calendar: Calendar) {
                    orientationCalendar = calendar
                    binding?.tvOrientationDate?.text = orientationCalendar?.time?.formatDate()
                    bottomSheet.dismiss()
                }
            })
            bottomSheet.show(supportFragmentManager, DateTimePickerBottomSheet::class.java.simpleName)
        }

        binding?.tvBeginsDate?.setOnClickListener {
            val bottomSheet = DateTimePickerBottomSheet()
            if (beginsDateCalendar != null){
                val bundle = Bundle()
                bundle.putInt(DateTimePickerBottomSheet.OLD_YEAR, beginsDateCalendar!!.get(Calendar.YEAR))
                bundle.putInt(DateTimePickerBottomSheet.OLD_MONTH, beginsDateCalendar!!.get(Calendar.MONTH))
                bundle.putInt(DateTimePickerBottomSheet.OLD_DAY_OF_MONTH, beginsDateCalendar!!.get(Calendar.DAY_OF_MONTH))
                bundle.putInt(DateTimePickerBottomSheet.OLD_HOUR, beginsDateCalendar!!.get(Calendar.HOUR_OF_DAY))
                bundle.putInt(DateTimePickerBottomSheet.OLD_MINUTE, beginsDateCalendar!!.get(Calendar.MINUTE))
                bottomSheet.arguments = bundle
            }
            bottomSheet.setCallBack(object : DateTimePickerBottomSheet.CallBack{
                override fun onSaveDateTimeClicked(calendar: Calendar) {
                    beginsDateCalendar = calendar
                    binding?.tvBeginsDate?.text = beginsDateCalendar?.time?.formatDate()
                    bottomSheet.dismiss()
                }
            })
            bottomSheet.show(supportFragmentManager, DateTimePickerBottomSheet::class.java.simpleName)
        }
    }

}