package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes

import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityBibleVerseBinding
import com.fadlurahmanf.starter_app_mvp.databinding.OverlayVerseBinding
import com.fadlurahmanf.starter_app_mvp.di.component.SideMenuComponent

class BibleVerseActivity : BaseActivity<ActivityBibleVerseBinding>(ActivityBibleVerseBinding::inflate) {
    private lateinit var component: SideMenuComponent

    private lateinit var overlayBinding:OverlayVerseBinding

    override fun injectView() {
        component = appComponent.sideMenuComponent().create()
        component.inject(this)
    }

    private var listString:ArrayList<String> = arrayListOf(
        "For the director of music. According to gittith. Of the Sons of Korah. A psalm.",
        "How lovely is your dwelling place, Lord Almighty!",
        "My soul yearns, even faints, for the courts of the Lord; my heart and my flesh cry out for the living God.",
        "Even the sparrow has found a home, and the swallow a nest for herself, where she may have her young— a place near your altar, Lord Almighty, my King and my God.",
        "Blessed are those who dwell in your house; they are ever praising you.",
        "Blessed are those whose strength is in you, whose hearts are set on pilgrimage.",
        "As they pass through the Valley of Baka, they make it a place of springs; the autumn rains also cover it with pools.",
        "They go from strength to strength, till each appears before God in Zion.",
        "Hear my prayer, Lord God Almighty; listen to me, God of Jacob.",
        "Look on our shield, O God; look with favor on your anointed one.",
        "Better is one day in your courts than a thousand elsewhere; I would rather be a doorkeeper in the house of my God than dwell in the tents of the wicked.",
        "For the Lord God is a sun and shield; the Lord bestows favor and honor; no good thing does he withhold from those whose walk is blameless.",
        "Lord Almighty, blessed is the one who trusts in you.",
        "For the director of music. According to gittith. Of the Sons of Korah. A psalm.",
        "How lovely is your dwelling place, Lord Almighty!",
        "My soul yearns, even faints, for the courts of the Lord; my heart and my flesh cry out for the living God.",
        "Even the sparrow has found a home, and the swallow a nest for herself, where she may have her young— a place near your altar, Lord Almighty, my King and my God.",
        "Blessed are those who dwell in your house; they are ever praising you.",
        "Blessed are those whose strength is in you, whose hearts are set on pilgrimage.",
        "As they pass through the Valley of Baka, they make it a place of springs; the autumn rains also cover it with pools.",
        "They go from strength to strength, till each appears before God in Zion.",
        "Hear my prayer, Lord God Almighty; listen to me, God of Jacob.",
        "Look on our shield, O God; look with favor on your anointed one.",
        "Better is one day in your courts than a thousand elsewhere; I would rather be a doorkeeper in the house of my God than dwell in the tents of the wicked.",
        "For the Lord God is a sun and shield; the Lord bestows favor and honor; no good thing does he withhold from those whose walk is blameless.",
        "Lord Almighty, blessed is the one who trusts in you.",
    )

    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(R.color.red, isLight = false)
        initTextSpan()

        overlayBinding = OverlayVerseBinding.inflate(layoutInflater)

        binding?.toolbar?.tvTitle?.setOnClickListener{
//            println("MASUK ${binding!!.tvVerseTitle.getLocationOnScreen()}")
        }
    }

    fun View.getLocationOnScreen(): Point
    {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return Point(location[0],location[1])
    }

    fun View.getCoordinatesY(): Int
    {
        var displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        var topOffset = displayMetric.heightPixels - (binding?.mainLayout?.measuredHeight?:0)

        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return location[1] - topOffset
    }

    private fun initTextSpan() {
        var list:ArrayList<SpannableString> = arrayListOf()
        for (i in listString.indices){
            if (i==0){
                val spannableString = SpannableString(listString[i])
                val textColor = ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_grey))
                spannableString.setSpan(textColor, 0, listString[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                var clickableSpan= object : ClickableSpan(){
                    override fun onClick(widget: View) {
                        println("MASUK ${list[i]}")
                        println("MASUK ${listString[i]}")
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                spannableString.setSpan(clickableSpan, 0, listString[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                list.add(spannableString)
            }else{
                val spannableString = SpannableString(" $i ${listString[i]}")
                val textColor = ForegroundColorSpan(ContextCompat.getColor(this, R.color.red))
                spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, i.toString().length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(textColor, i.toString().length + 2, (i.toString().length + 2 + listString[i].length), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                var clickableSpan= object :ClickableSpan(){
                    @SuppressLint("ClickableViewAccessibility")
                    override fun onClick(widget: View) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            widget.setOnTouchListener { v, event ->
                                showOverlay(event)
                                true
                            }
                        }
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                spannableString.setSpan(clickableSpan, 1 + i.toString().length, (1 + i.toString().length + 1 + listString[i].length), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                list.add(spannableString)
            }
        }

        var spannableStringBuilder = SpannableStringBuilder()

        list.forEach {
            spannableStringBuilder.append(it)
        }

        binding?.tvText?.movementMethod = LinkMovementMethod.getInstance()
        binding?.tvText?.text = spannableStringBuilder

        binding?.tvText2?.movementMethod = LinkMovementMethod.getInstance()
        binding?.tvText2?.text = spannableStringBuilder
    }

    private fun showOverlay(event: MotionEvent) {
        val scrollViewHeight = binding!!.scrollView.height
        var positionYOverlay:Float = event.y
        println("MASUK AWAL ${positionYOverlay}")
        if (event.y/scrollViewHeight >= 0.7){
            positionYOverlay -= 200f
        }else{
            positionYOverlay += 400f
        }
        println("MASUK AKHIR ${positionYOverlay}")

        binding?.rlContent?.removeView(overlayBinding.fl)
        overlayBinding.fl.translationY = positionYOverlay
        binding?.rlContent?.addView(overlayBinding.fl)
    }


}