package com.fadlurahmanf.starter_app_mvp.ui.study_group.widget

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseBottomSheetFragment
import com.fadlurahmanf.starter_app_mvp.core.extension.formatDate3
import com.fadlurahmanf.starter_app_mvp.databinding.BottomsheetDateTimePickerBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class DateTimePickerBottomSheet:BaseBottomSheetFragment<BottomsheetDateTimePickerBinding>(BottomsheetDateTimePickerBinding::inflate) {

    companion object{
        const val OLD_DAY_OF_MONTH = "OLD_DAY_OF_MONTH"
        const val OLD_MONTH = "OLD_MONTH"
        const val OLD_YEAR = "OLD_YEAR"
        const val OLD_HOUR = "OLD_HOUR"
        const val OLD_MINUTE = "OLD_MINUTE"

        const val IS_EDIT = "IS_EDIT"
    }

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    private var isToday:Boolean = false

    override fun injectView() {}

    private var currentDate = Calendar.getInstance()
    private var oldDateTime = Calendar.getInstance()
    private var selectedDateTime = oldDateTime


    @RequiresApi(Build.VERSION_CODES.M)
    override fun setup() {
        binding?.timePicker?.setIs24HourView(true)
        setupDateTime()
        binding?.tvDate?.setOnClickListener {
            val datePickerDialog = DatePickerDialog()
            val bundle = Bundle()
            bundle.putBoolean(DatePickerDialog.ENABLED_MIN_DATE, true)
            bundle.putInt(DatePickerDialog.OLD_YEAR, selectedDateTime.get(Calendar.YEAR))
            bundle.putInt(DatePickerDialog.OLD_MONTH, selectedDateTime.get(Calendar.MONTH))
            bundle.putInt(DatePickerDialog.OLD_DAY_OF_MONTH, selectedDateTime.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.arguments = bundle
            datePickerDialog.setCallBack(object : DatePickerDialog.CallBack{
                override fun onDateClicked(calendar: Calendar) {
                    selectedDateTime.apply {
                        set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                        set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                        set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
                    }
                    checkIsToday()
                    checkButton()
                    binding?.tvDate?.text = selectedDateTime.time.formatDate3()
                    datePickerDialog.dismiss()
                }
            })
            datePickerDialog.show(this.parentFragmentManager, NumberPickerBottomSheet::class.java.simpleName)
        }

        binding?.timePicker?.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectedDateTime.apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            checkIsToday()
            checkButton()
            if (isToday && selectedDateTime.time.before(Calendar.getInstance().time)){
                val snackBar = Snackbar.make(binding?.btnSave?.rootView!!, "Select time after ${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:${Calendar.getInstance().get(Calendar.MINUTE)}", Snackbar.LENGTH_LONG)
                val view = snackBar.view
                val layoutParams =  view.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity= Gravity.TOP
                layoutParams.topMargin = 150
                view.layoutParams = layoutParams
                snackBar.show()
            }
        }

        binding?.btnSave?.setOnClickListener {
            callBack.onSaveDateTimeClicked(selectedDateTime)
        }
        checkButton()
    }

    private fun checkButton() {
        if (isToday && selectedDateTime.time.before(Calendar.getInstance().time)){
            binding?.btnSave?.background = ContextCompat.getDrawable(requireContext(), R.drawable.grey_corner_10)
            binding?.btnSave?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_grey))
            binding?.btnSave?.isEnabled = false
        }else{
            binding?.btnSave?.background = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_red_corner_10)
            binding?.btnSave?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding?.btnSave?.isEnabled = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupDateTime() {
        val oldYear : Int = arguments?.getInt(OLD_YEAR) ?: Calendar.getInstance().get(Calendar.YEAR)
        val oldMonth : Int = arguments?.getInt(OLD_MONTH) ?: Calendar.getInstance().get(Calendar.MONTH)
        val oldMonthDay : Int = arguments?.getInt(OLD_DAY_OF_MONTH) ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val oldHour : Int = arguments?.getInt(OLD_HOUR) ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val oldMinute : Int = arguments?.getInt(OLD_MINUTE) ?: Calendar.getInstance().get(Calendar.MINUTE)
        oldDateTime.apply {
            set(Calendar.YEAR, oldYear)
            set(Calendar.MONTH, oldMonth)
            set(Calendar.DAY_OF_MONTH, oldMonthDay)
            set(Calendar.HOUR_OF_DAY, oldHour)
            set(Calendar.MINUTE, oldMinute)
            set(Calendar.SECOND, 0)
        }
        checkIsToday()
        if (isToday && oldDateTime.time.before(Calendar.getInstance().time)){
            oldDateTime.apply {
                add(Calendar.HOUR_OF_DAY, 1)
            }
        }

        selectedDateTime = oldDateTime

        binding?.tvDate?.text = selectedDateTime.time.formatDate3()
        binding?.timePicker?.hour = selectedDateTime.get(Calendar.HOUR_OF_DAY)
        binding?.timePicker?.minute = selectedDateTime.get(Calendar.MINUTE)

    }

    private fun checkIsToday(){
        isToday = (selectedDateTime.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                && selectedDateTime.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) && selectedDateTime.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR))
    }

    interface CallBack{
        fun onSaveDateTimeClicked(calendar: Calendar)
    }
}