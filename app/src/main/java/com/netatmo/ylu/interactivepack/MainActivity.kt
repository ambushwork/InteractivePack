package com.netatmo.ylu.interactivepack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.netatmo.ylu.interactivepack.coordinator.CoordinatorCompActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, CoordinatorCompActivity::class.java)
        startActivity(intent)
        //CoordinatorCompActivity.startActivity(this)
    }
}
