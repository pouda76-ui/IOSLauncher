package com.example.ioslauncher

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = TextView(this).apply {
            text = "IOSLauncher\n\nTESZT – ha ezt látod, az app elindult."
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setBackgroundColor(Color.rgb(70, 150, 245))
        }
        setContentView(view)
    }
}
