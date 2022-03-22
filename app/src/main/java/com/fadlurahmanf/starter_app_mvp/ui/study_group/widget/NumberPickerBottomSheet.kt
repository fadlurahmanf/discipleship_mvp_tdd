package com.fadlurahmanf.starter_app_mvp.ui.study_group.widget

import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseBottomSheetFragment
import com.fadlurahmanf.starter_app_mvp.base.BaseDialog
import com.fadlurahmanf.starter_app_mvp.databinding.BottomsheetNumberPickerBinding

class NumberPickerBottomSheet:BaseBottomSheetFragment<BottomsheetNumberPickerBinding>(BottomsheetNumberPickerBinding::inflate) {
    private var oldValue = 6
    private var selected = 6

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    companion object{
        const val OLD_VALUE = "OLD_VALUE"
    }

    override fun setup() {
        oldValue = arguments?.getInt(OLD_VALUE) ?: 6
        binding?.numberPicker?.minValue = 6
        binding?.numberPicker?.maxValue = 15
        binding?.numberPicker?.value = oldValue
        binding?.numberPicker?.wrapSelectorWheel = false
        binding?.numberPicker?.setOnValueChangedListener { picker, _, newVal ->
            picker.value
            selected = newVal
            if (newVal != oldValue){
                binding?.btnSave?.background = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_red_corner_10)
                binding?.btnSave?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding?.btnSave?.isEnabled = true
            }else{
                binding?.btnSave?.background = ContextCompat.getDrawable(requireContext(), R.drawable.grey_corner_10)
                binding?.btnSave?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_grey))
                binding?.btnSave?.isEnabled = false
            }
        }

        binding?.btnSave?.setOnClickListener {
            callBack.onSaveClicked(selected)
        }
    }

    override fun injectView() {

    }

    interface CallBack{
        fun onSaveClicked(value:Int)
    }
}