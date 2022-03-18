package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.widget

import android.icu.util.Calendar
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseDialog
import com.fadlurahmanf.starter_app_mvp.databinding.DialogAlarmTimePickerBinding
import java.text.SimpleDateFormat
import java.util.*

class AlarmTimePickerDialog:BaseDialog<DialogAlarmTimePickerBinding>(DialogAlarmTimePickerBinding::inflate) {
    private var sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    @RequiresApi(Build.VERSION_CODES.N)
    private var calendar = Calendar.getInstance().time
    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }


    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun setup() {
        calendar.seconds = 0
        binding?.timePicker?.setIs24HourView(true)
        binding?.timePicker?.setOnTimeChangedListener(object :TimePicker.OnTimeChangedListener{
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                calendar.hours = hourOfDay
                calendar.minutes = minute
            }
        })

        binding?.btnSave?.setOnClickListener {
            callBack.onSaveDate(calendar)
        }
    }

    override fun injectView() {

    }


    interface CallBack{
        fun onSaveDate(date:Date)
    }
}