package com.netatmo.ylu.interactivepack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.netatmo.ylu.interactivepack.coordinator.CardData
import com.netatmo.ylu.interactivepack.coordinator.CoordinatorCompActivity
import com.netatmo.ylu.interactivepack.vectoranimation.VectorActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(intent)
        //CoordinatorCompActivity.startActivity(this)
        val adapter = MainAdapter(this)
        adapter.setData(createCardData())
        main_list.adapter = adapter
        main_list.layoutManager = LinearLayoutManager(this)
    }


    fun createCardData(): ArrayList<CardData> {
        val list = ArrayList<CardData>()
        val intent = Intent(this, CoordinatorCompActivity::class.java)
        list.add(CardData("Coordinator Demo", intent))
        val intent2 = Intent(this, VectorActivity::class.java)
        list.add(CardData("Vector Animation Demo", intent2))
        return list
    }
}
