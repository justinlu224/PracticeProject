package com.example.practiceproject.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TestView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    val paint = Paint()
    init {
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL

    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(100f,100f,100f,100f,0f,360f,true,paint)
    }

}