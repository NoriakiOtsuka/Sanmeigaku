package com.example.sanmeigaku.View

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import com.example.sanmeigaku.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class SuuriGraphMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val TAG: String = "SuuriGraphMarkerView"

    /**
     * Display and update marker text
     */
    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry, highlight: Highlight) {
        Log.i(TAG, "refreshContent: ${e}, ${highlight}")

        val legendText: TextView = findViewById(R.id.markerText)
        val xValue = e.x.toInt()
        val yValue = e.y
        val yDecimalValue = yValue.mod(1.0)
        if (yDecimalValue > 0) {
            legendText.text = "(${xValue}, ${yValue})"
        } else {
            legendText.text = "(${xValue}, ${yValue.toInt()})"
        }

        super.refreshContent(e, highlight)
    }

    /**
     * Adjust marker display position
     */
    override fun getOffset(): MPPointF {
        return MPPointF((width / 8f) + 5f, -height.toFloat())
    }
}