package com.fadlurahmanf.starter_app_mvp.core.custom_lib

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.R


class CustomToolbar4 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs) {

    private var attributes :TypedArray
    private var textView: TextView
    var leftIcon:ImageView
    var rightIcon:ImageView

    enum class AppBarColor{
        RED, WHITE
    }
    private enum class AppBarBack{
        BACK, CLOSE
    }


    private lateinit var backType:AppBarBack

    init {
        inflate(context, R.layout.custom_toolbar, this)
        textView = findViewById(R.id.custom_text)
        leftIcon = findViewById(R.id.iv_left)
        rightIcon = findViewById(R.id.iv_right)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)

//        attributes.hasValue(R.styleable.CustomToolbar_backType)
        setText(attributes.getString(R.styleable.CustomToolbar_title)?:"")

        backType = if (attributes.getInt(R.styleable.CustomToolbar_backType, 0) == 0) AppBarBack.BACK else AppBarBack.CLOSE
        setLeftIcon(backType)
    }

    private fun setLeftIcon(type:AppBarBack){
        if (type == AppBarBack.BACK){
            leftIcon.setImageResource(R.drawable.ic_back_red)
        }else if (type == AppBarBack.CLOSE){
            leftIcon.setImageResource(R.drawable.ic_close)
        }
    }

    private fun setText(text:String){
        textView.text = text
    }

    private fun getText(): String? {
        return attributes.getString(R.styleable.CustomToolbar_title)
    }
}