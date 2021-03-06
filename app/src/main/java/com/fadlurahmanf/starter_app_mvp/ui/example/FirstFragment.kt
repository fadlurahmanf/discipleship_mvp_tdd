package com.fadlurahmanf.starter_app_mvp.ui.example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseFragment
import com.fadlurahmanf.starter_app_mvp.core.event.ChangeText
import com.fadlurahmanf.starter_app_mvp.core.utils.RxBus
import com.fadlurahmanf.starter_app_mvp.data.repository.example.ExampleRepository
import com.fadlurahmanf.starter_app_mvp.databinding.FragmentFirstBinding
import com.fadlurahmanf.starter_app_mvp.di.component.ExampleComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


class FirstFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {
    private lateinit var component: ExampleComponent

    @Inject
    lateinit var exampleRepository: ExampleRepository

    override fun injectView() {
        component = (requireActivity().applicationContext as BaseApp).appComponent.exampleComponent().create()
        component.inject(this)
    }

    override fun setup() {
        binding?.tv?.setOnClickListener {
            exampleRepository.text1 = "TES BARU"
            RxBus.publish(ChangeText())
        }
    }

}