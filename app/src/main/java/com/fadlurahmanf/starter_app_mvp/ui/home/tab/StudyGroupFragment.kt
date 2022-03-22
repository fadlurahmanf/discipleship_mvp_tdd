package com.fadlurahmanf.starter_app_mvp.ui.home.tab

import android.content.Intent
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
import com.fadlurahmanf.starter_app_mvp.data.repository.study_group.StudyGroupRepository
import com.fadlurahmanf.starter_app_mvp.data.response.study_group.StudyGroupDetailResponse
import com.fadlurahmanf.starter_app_mvp.databinding.FragmentStudyGroupBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.home.BaseDrawer
import com.fadlurahmanf.starter_app_mvp.ui.home.MainActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.StudyGroupFragmentContract
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.StudyGroupFragmentPresenter
import com.fadlurahmanf.starter_app_mvp.ui.study_group.CreateStudyGroupDetailActivity
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

    @Inject
    lateinit var studyGroupRepository: StudyGroupRepository

    override fun setup() {
        initAction()
        initView()
        if (studyGroupRepository.studyGroupDetail != null){
            presenter.getStudyGroupDetailSecond()
            setData()
        }else{
            presenter.getStudyGroupDetail()
        }
    }

    private fun initAction() {
        binding?.tvEditDetail?.setOnClickListener {
            val intent = Intent(requireContext(), CreateStudyGroupDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getStudyGroupDetailSecond()
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

    private fun setData(){
        binding?.tvStudyTopic?.text = studyGroupRepository.studyGroupDetail?.study?.name
        binding?.tvStudyGroupCapacity?.text = studyGroupRepository.studyGroupDetail?.capacity?.toString() + " Participants"
        binding?.tvWeeklyMeeting?.text = studyGroupRepository.studyGroupDetail?.weeklyMeetingTime
        binding?.tvOrientationDate?.text = studyGroupRepository.studyGroupDetail?.orientationDate?.isoDateTimeToDate()?.formatDate()
        binding?.tvStudyBeginsDate?.text = studyGroupRepository.studyGroupDetail?.startDate?.isoDateTimeToDate()?.formatDate()
        binding?.tvDurationStudy?.text = studyGroupRepository.studyGroupDetail?.meeting?.datas?.size?.toString() + " sessions"
    }


    override fun getStudyGroupDetailSuccess() {
        binding?.llMain?.visibility = View.VISIBLE
        binding?.llProgress?.visibility = View.GONE
        setData()
    }

    override fun getStudyGroupDetailFailed(message:String?) {
        binding?.llMain?.visibility = View.GONE
        binding?.llProgress?.visibility = View.VISIBLE
        showSnackBar(message)
    }
}