package com.example.sanmeigaku.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.R
import kotlin.math.cos
import kotlin.math.sin

class MeishikiChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    private val TAG: String = "MeishikiChart"
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of kan-shi number received from the assessment activity */
    private val mYearKanShiNo: Int = activity.mYearKanShiNo
    private val mMonthKanShiNo: Int = activity.mMonthKanShiNo
    private val mDayKanShiNo: Int = activity.mDayKanShiNo

    /** Main line connecting the coordinates of kan-shi number */
    private val mainLine: Paint = Paint().apply {
        color = resources.getColor(R.color.meishiki_chart_main_line)
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    /** Mathematics reference line */
    private val referenceLine: Paint = Paint().apply {
        color = resources.getColor(R.color.meishiki_chart_reference_line)
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    /** Point when all coordinates of kan-shi number are the same */
    private val mainPoint: Paint = Paint().apply {
        color = resources.getColor(R.color.meishiki_chart_main_line)
        style = Paint.Style.FILL
    }

    /**
     * Draw a chart connecting the coordinates of the kan-shi number
     */
    override fun onDraw(canvas: Canvas) {
        val cx = width / 2
        val cy = height / 2
        val radius =
            if (width > height) {
                height / 2
            } else {
                width / 2
            }
        Log.i(TAG, "onDraw: width: $width, height: $height in the Meishiki chart")

        val x1 = cx + radius * sin(2 * Math.PI * (mYearKanShiNo - 1) / 60)
        val y1 = cy - radius * cos(2 * Math.PI * (mYearKanShiNo - 1) / 60)
        val x2 = cx + radius * sin(2 * Math.PI * (mMonthKanShiNo - 1) / 60)
        val y2 = cy - radius * cos(2 * Math.PI * (mMonthKanShiNo - 1) / 60)
        val x3 = cx + radius * sin(2 * Math.PI * (mDayKanShiNo - 1) / 60)
        val y3 = cy - radius * cos(2 * Math.PI * (mDayKanShiNo - 1) / 60)

        canvas.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), mainLine)
        canvas.drawLine(x2.toFloat(), y2.toFloat(), x3.toFloat(), y3.toFloat(), mainLine)
        canvas.drawLine(x3.toFloat(), y3.toFloat(), x1.toFloat(), y1.toFloat(), mainLine)

        canvas.drawLine((cx - radius).toFloat(), cy.toFloat(), (cx + radius).toFloat(), cy.toFloat(), referenceLine)
        canvas.drawLine(cx.toFloat(), (cy - radius).toFloat(), cx.toFloat(), (cy + radius).toFloat(), referenceLine)
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), referenceLine)

        if ((mYearKanShiNo == mMonthKanShiNo) && (mYearKanShiNo == mDayKanShiNo))
            canvas.drawCircle(x1.toFloat(), y1.toFloat(), 10F, mainPoint)

        Log.i(TAG, "onDraw: Meishiki chart is drawn.")
    }
}