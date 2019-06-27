package com.netatmo.ylu.interactivepack.coordinator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netatmo.ylu.interactivepack.R
import kotlinx.android.synthetic.main.demo_activity_coordinator_layout.*

class CoordinatorCompActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, this::class.java)
            context.startActivity(intent)
        }
    }

    var headerHeight: Int? = 0
    var recyclerView: RecyclerView? = null
    var currentPosition: Int? = 0
    var linearLayoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_coordinator_layout)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView?.adapter = ContentAdapter()
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                headerHeight = list_header.height
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val view = recyclerView.layoutManager?.findViewByPosition(currentPosition!!.plus(1)) ?: return
                if (view.top < headerHeight!!) {
                    list_header.y = -(headerHeight!! - view.top).toFloat()
                } else {
                    list_header.y = 0f
                }
                if (currentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    currentPosition = linearLayoutManager.findFirstVisibleItemPosition()

                }


            }
        })
        back_top_button.setOnClickListener {
            recyclerView!!.smoothScrollToPosition(0)
        }
    }
}