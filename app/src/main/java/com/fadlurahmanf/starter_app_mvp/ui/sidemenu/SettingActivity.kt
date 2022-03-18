package com.fadlurahmanf.starter_app_mvp.ui.sidemenu

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.core.event.ChangeText
import com.fadlurahmanf.starter_app_mvp.core.utils.NotificationUtils
import com.fadlurahmanf.starter_app_mvp.core.utils.RxBus
import com.fadlurahmanf.starter_app_mvp.data.model.example.NotificationData
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.databinding.ActivitySettingBinding
import com.fadlurahmanf.starter_app_mvp.di.component.SideMenuComponent
import com.fadlurahmanf.starter_app_mvp.ui.core.worker.AlarmWorker
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.SelectLanguageActivity
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.widget.AlarmTimePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class SettingActivity : BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private lateinit var component: SideMenuComponent
    private var isAssignmentReminder = false
    private var assignmentReminderTime : Date ?= null

    override fun injectView() {
        component = appComponent.sideMenuComponent().create()
        component.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(isFullScreen = true)
        initView()
        initData()
        initAction()
        initText()
        setText()
    }

    private fun initData() {
        isAssignmentReminder = authRepository.assignmentReminderTime != null
        binding?.layoutReminder?.ivSwitch?.isChecked = isAssignmentReminder
        assignmentReminderTime = authRepository.assignmentReminderTime
        binding?.clManageReminder?.visibility = if (isAssignmentReminder) View.VISIBLE else View.GONE
    }

    @Inject
    lateinit var notificationUtils: NotificationUtils

    private var alarmTimePickerDialog:AlarmTimePickerDialog ?= null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun initAction() {
        binding?.layoutLanguage?.clSetting?.setOnClickListener {
//            val intent = Intent(this, SelectLanguageActivity::class.java)
//            startActivity(intent)

            var alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
            alarmManager.cancel(pendingIntent)
        }


        binding?.toolbar?.tvTitle?.setOnClickListener {
//            notificationUtils.showNotification(NotificationData(
//                notificationTitle = Random.nextInt(999).toString(),
//                notificationContent = Random.nextInt(999).toString()
//            ))
//            var alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent = Intent(this, AlarmReceiver::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//            alarmManager.cancel(pendingIntent)
//            var calendar = Calendar.getInstance().apply {
//                set(Calendar.DAY_OF_MONTH, 18)
//                set(Calendar.HOUR_OF_DAY, 16)
//                set(Calendar.MINUTE, 57)
//                set(Calendar.SECOND, 0)
//            }
//            println("SET ALARM AT  ${calendar.time}")
//
//            alarmManager.setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                1000 * 15,
//                pendingIntent
//            )

            var periodic = PeriodicWorkRequestBuilder<AlarmWorker>(1, TimeUnit.MINUTES)
                .build()
            println("MASUK ${periodic.id}")
            WorkManager.getInstance(this).enqueue(periodic)
            observeWork(periodic.id)

        }

        binding?.layoutReminder?.ivSwitch?.setOnClickListener {
            isAssignmentReminder = !isAssignmentReminder
            binding?.layoutReminder?.ivSwitch?.isChecked = isAssignmentReminder
        }
        binding?.layoutReminder?.ivSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding?.clManageReminder?.visibility = View.VISIBLE
            }else{
                binding?.clManageReminder?.visibility = View.GONE
            }
        }

        binding?.clManageReminder?.setOnClickListener {
            showAlarmTimePickerDialog()
        }
    }

    fun observeWork(id:UUID){
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(id).observe(this) {
            if (it.state == WorkInfo.State.SUCCEEDED){
                println("MASUK")
            }
        }
    }

    @Inject
    lateinit var authRepository: AuthRepository

    private fun showAlarmTimePickerDialog(){
        alarmTimePickerDialog = AlarmTimePickerDialog()
        alarmTimePickerDialog?.setCallBack(object : AlarmTimePickerDialog.CallBack{
            override fun onSaveDate(date: Date) {
                assignmentReminderTime = date
                authRepository.assignmentReminderTime = date
                initText()
                dismissAlarmTimePickerDialog()
            }
        })
        alarmTimePickerDialog?.show(supportFragmentManager, AlarmTimePickerDialog::class.java.simpleName)
    }

    private fun dismissAlarmTimePickerDialog(){
        if (alarmTimePickerDialog != null){
            alarmTimePickerDialog?.dismiss()
            alarmTimePickerDialog = null
        }
    }

    @Inject
    lateinit var appRepository:AppRepository

    private fun initView() {
        //language
        var language = binding?.layoutLanguage
        language?.clSetting?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        language?.tvSettingTitle?.text = "Language"
        language?.tvDescription?.text = appRepository.paramsLanguage?.name

        var reminder = binding?.layoutReminder
        reminder?.tvSettingTitle?.text = "Assignment reminder"
        reminder?.tvSettingSubTitle?.text = "Set a time to get reminder on assignment"
        reminder?.tvSettingSubTitle?.visibility = View.VISIBLE
    }

    private fun initText(){
        var language = binding?.layoutLanguage
        language?.tvDescription?.text = appRepository.paramsLanguage?.name

        if (assignmentReminderTime == null){
            binding?.tvSetTime?.text = "Set time"
        }else{
            var sdf = SimpleDateFormat("HH:mm")
            binding?.tvSetTime?.text = sdf.format(assignmentReminderTime!!)
        }
    }

    override fun setText() {
        super.setText()
        initText()
    }
}

class AlarmReceiver:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        var ins = Intent("com.alarm.receiver")
        println("MASUK ON RECEIVE ${Calendar.getInstance().time}")
    }
}