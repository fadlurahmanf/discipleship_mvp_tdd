package com.fadlurahmanf.starter_app_mvp.ui.study_group.widget

import android.os.Build
import com.fadlurahmanf.starter_app_mvp.base.BaseDialog
import com.fadlurahmanf.starter_app_mvp.databinding.DialogDatePickerBinding
import java.util.*

class DatePickerDialog : BaseDialog<DialogDatePickerBinding>(DialogDatePickerBinding::inflate) {
    companion object {
        const val OLD_DAY_OF_MONTH = "OLD_DAY_OF_MONTH"
        const val OLD_MONTH = "OLD_MONTH"
        const val OLD_YEAR = "OLD_YEAR"

        const val ENABLED_MIN_DATE = "ENABLED_MIN_DATE"
        const val MIN_DATE = "MIN_DATE"

        const val ENABLED_MAX_DATE = "ENABLED_MAX_DATE"
        const val MAX_DATE = "MAX_DATE"
    }

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    private var selectedCalendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    override fun setup() {
        setupCalendar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding?.datePicker?.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                selectedCalendar.set(Calendar.YEAR, year)
                selectedCalendar.set(Calendar.MONTH, monthOfYear)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                callBack.onDateClicked(selectedCalendar)
            }
        }else{

        }
    }

    private fun setupCalendar() {
        val enabledMinDate : Boolean = arguments?.getBoolean(ENABLED_MIN_DATE, true) ?: true
        if (enabledMinDate){
            val minDate : Long = arguments?.getLong(MIN_DATE, Calendar.getInstance().timeInMillis) ?: Calendar.getInstance().timeInMillis
            binding?.datePicker?.minDate = minDate
        }

        val enabledMaxDate : Boolean = arguments?.getBoolean(ENABLED_MAX_DATE, false) ?: false
        if (enabledMaxDate){
            val defaultMaxDate = Calendar.getInstance()
            defaultMaxDate.add(Calendar.DAY_OF_MONTH, 1)
            val maxDate : Long = arguments?.getLong(MAX_DATE, defaultMaxDate.timeInMillis) ?: defaultMaxDate.timeInMillis
            binding?.datePicker?.maxDate = maxDate
        }

        val oldYear : Int = arguments?.getInt(OLD_YEAR) ?: Calendar.getInstance().get(Calendar.YEAR)
        val oldMonth : Int = arguments?.getInt(OLD_MONTH) ?: Calendar.getInstance().get(Calendar.MONTH)
        val oldMonthDay : Int = arguments?.getInt(OLD_DAY_OF_MONTH) ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        binding?.datePicker?.updateDate(oldYear, oldMonth, oldMonthDay)
    }

    override fun injectView() {
    }

    interface CallBack{
        fun onDateClicked(calendar: Calendar)
    }
}