package com.example.snaphelper1

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var position: Int

    init {
        position = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var imageList: ArrayList<String> = arrayListOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        var snapHelper: SnapHelper = PagerSnapHelper()
        val sampleAdpater = SampleAdapter(this, imageList)
        recyclerView.adapter = sampleAdpater
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                (recyclerView.adapter as SampleAdapter).position = position
                (recyclerView.adapter as SampleAdapter).notifyDataSetChanged()
            }
        })

        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                child?.let {
                    if (position - 1 == rv.getChildAdapterPosition(it) || position + 1 == rv.getChildAdapterPosition(it)) {
                        return true
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }
}
