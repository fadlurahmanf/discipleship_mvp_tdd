package com.fadlurahmanf.starter_app_mvp.ui.home.tab

import android.content.Intent
import android.view.View
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpFragment
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityMainBinding
import com.fadlurahmanf.starter_app_mvp.databinding.FragmentGroupBoardBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.auth.LoginActivity
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.GuestModeActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.MainActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.adapter.PostAdapter
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.GroupBoardContract
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.GroupBoardPresenter
import javax.inject.Inject

class GroupBoardFragment : BaseMvpFragment<FragmentGroupBoardBinding, GroupBoardPresenter>(FragmentGroupBoardBinding::inflate),
    GroupBoardContract.View, PostAdapter.PostClickCallBack {
    private lateinit var component: HomeComponent
    override fun injectView() {
        component = (this.requireActivity().applicationContext as BaseApp).appComponent.homeComponent().create()
        component.inject(this)
    }

    @Inject
    lateinit var presenter: GroupBoardPresenter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun setup() {
        initAction()
        initAdapter()
        presenter.getAllPostFirst()
    }

    private fun initAction() {
        binding?.toolbar?.icLeftMenu?.setOnClickListener {
            (this.requireActivity() as MainActivity).openCloseDrawer()
        }

        binding?.swipeRefresh?.setOnRefreshListener {
            binding?.swipeRefresh?.isRefreshing = true
            presenter.getAllPost()
        }

        (requireActivity() as MainActivity).binding?.navViewLayout?.btnLogin?.setOnClickListener {
            val intent1 = Intent(this.requireActivity(), GuestModeActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val intent2 = Intent(this.requireActivity(), LoginActivity::class.java)
            startActivity(intent1)
            startActivity(intent2)
        }
    }

    private lateinit var adapter:PostAdapter
    private var listPost:ArrayList<PostResponse> = arrayListOf()
    private fun initAdapter() {
        adapter = PostAdapter(listPost)
        adapter.setOnClickCallBack(this)
        binding?.rvPost?.adapter = adapter
    }

    private fun refreshRecycleView(){
        adapter.notifyDataSetChanged()
    }

    override fun getAllPostFirstLoading() {
        binding?.llPostShimmer?.visibility = View.VISIBLE
        binding?.rvPost?.visibility = View.GONE
    }

    override fun getAllPostFirstLoaded(listPost: List<PostResponse>) {
        binding?.swipeRefresh?.isRefreshing = false
        binding?.llPostShimmer?.visibility = View.GONE
        binding?.rvPost?.visibility = View.VISIBLE
        this.listPost.clear()
        this.listPost.addAll(listPost)
        refreshRecycleView()
    }

    override fun getAllPostLoaded(listPost: List<PostResponse>) {
        binding?.swipeRefresh?.isRefreshing = false
        this.listPost.clear()
        this.listPost.addAll(listPost)
        refreshRecycleView()
    }

    override fun getAllPostFailed(message: String?) {
        showSnackBar(message)
    }

    override fun onPostClicked(post: PostResponse?) {

    }

    override fun onMoreClicked(post: PostResponse?) {
        showOkDialog(
            title = "TES",
            content = "HALO"
        )
    }

}