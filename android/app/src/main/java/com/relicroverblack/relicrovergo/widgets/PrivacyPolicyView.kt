package com.relicroverblack.relicrovergo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.relicroverblack.relicrovergo.R

class PrivacyPolicyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textViewTitle: TextView
    private val imageViewArrow: ImageView

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.privacy_policy, this, true)
        textViewTitle = findViewById(R.id.text_view_title)
        imageViewArrow = findViewById(R.id.image_view_arrow)
        initAttributes(context, attrs)
    }

    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PrivacyPolicyView)

        val title = attributes.getString(R.styleable.PrivacyPolicyView_titleText)
        textViewTitle.text = title

        attributes.recycle()
    }

    fun setTitle(title: String) {
        textViewTitle.text = title
    }
}