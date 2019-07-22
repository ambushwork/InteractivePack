package com.netatmo.ylu.interactivepack.glowingview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.netatmo.ylu.interactivepack.R
import kotlinx.android.synthetic.main.demo_activity_glowing_view.*

class GlowingViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_glowing_view)
        glowing_view.setImageResource(R.drawable.apple)
        glowing_view.glow()
    }
}