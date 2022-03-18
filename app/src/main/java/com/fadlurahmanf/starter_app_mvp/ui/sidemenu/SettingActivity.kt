package com.fadlurahmanf.starter_app_mvp.ui.sidemenu

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.SelectLanguageActivity
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.widget.AlarmTimePickerDialog
import java.text.SimpleDateFormat
import java.util.*
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

    private fun initAction() {
        binding?.layoutLanguage?.clSetting?.setOnClickListener {
            val intent = Intent(this, SelectLanguageActivity::class.java)
            startActivity(intent)
        }

        binding?.toolbar?.tvTitle?.setOnClickListener {
            notificationUtils.showNotification(NotificationData(
                notificationTitle = Random.nextInt(999).toString(),
                notificationContent = Random.nextInt(999).toString()
            ))
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