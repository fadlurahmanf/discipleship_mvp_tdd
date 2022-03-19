package com.fadlurahmanf.starter_app_mvp.ui.home.tab

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpFragment
import com.fadlurahmanf.starter_app_mvp.core.extension.formatDate
import com.fadlurahmanf.starter_app_mvp.core.extension.isoDateTimeToDate
import com.fadlurahmanf.starter_app_mvp.data.response.study_group.StudyGroupDetailResponse
import com.fadlurahmanf.starter_app_mvp.databinding.FragmentStudyGroupBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.home.BaseDrawer
import com.fadlurahmanf.starter_app_mvp.ui.home.MainActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.StudyGroupFragmentContract
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.StudyGroupFragmentPresenter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class StudyGroupFragment : BaseMvpFragment<FragmentStudyGroupBinding, StudyGroupFragmentPresenter>(FragmentStudyGroupBinding::inflate),
    StudyGroupFragmentContract.View {
    private lateinit var component:HomeComponent
    override fun injectView() {
        component = appComponent.homeComponent().create()
        component.inject(this)
    }

    override fun setup() {
        initAction()
        initView()
        presenter.getStudyGroupDetail()
    }

    private fun initAction() {
        binding?.tvEditDetail?.setOnClickListener {
        }
    }

    private fun initView() {
        binding?.toolbar?.tvTitle?.visibility = View.GONE
        binding?.toolbar?.icLeftMenu?.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_menu_white)
        binding?.toolbar?.icLeftMenu?.layoutParams?.height = resources.getDimensionPixelOffset(R.dimen.dimen_24dp)
        binding?.toolbar?.icLeftMenu?.layoutParams?.width = resources.getDimensionPixelOffset(R.dimen.dimen_24dp)
        binding?.toolbar?.icLeftMenu?.setOnClickListener {
            (context as MainActivity).openCloseDrawer()
        }
    }

    @Inject
    lateinit var presenter: StudyGroupFragmentPresenter
    override fun initPresenterView() {
        presenter.view = this
    }

    override fun getStudyGroupDetailLoading() {
        binding?.llMain?.visibility = View.GONE
        binding?.llProgress?.visibility = View.VISIBLE
    }


    override fun getStudyGroupDetailSuccess(data:StudyGroupDetailResponse) {
        binding?.llMain?.visibility = View.VISIBLE
        binding?.llProgress?.visibility = View.GONE

        binding?.tvStudyTopic?.text = data.study?.name
        binding?.tvStudyGroupCapacity?.text = data.capacity?.toString() + " Participants"
        binding?.tvWeeklyMeeting?.text = data.weeklyMeetingTime
        binding?.tvOrientationDate?.text = data.orientationDate?.isoDateTimeToDate()?.formatDate()
        binding?.tvStudyBeginsDate?.text = data.startDate?.isoDateTimeToDate()?.formatDate()
        binding?.tvDurationStudy?.text = data.meeting?.datas?.size?.toString() + " sessions"
    }

    override fun getStudyGroupDetailFailed(message:String?) {
        binding?.llMain?.visibility = View.GONE
        binding?.llProgress?.visibility = View.VISIBLE
        showSnackBar(message)
    }
}