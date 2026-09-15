package com.example.ioslauncher

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = TextView(this)
        title.text = "IOSLauncher\n\nMűködik!"
        title.textSize = 28f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER
        title.setBackgroundColor(Color.rgb(70, 150, 245))

        setContentView(title)
    }
}
