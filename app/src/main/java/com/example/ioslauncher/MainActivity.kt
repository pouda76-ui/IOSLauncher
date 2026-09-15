package com.example.ioslauncher

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.BLACK
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(18, 28, 18, 18)
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(120, 205, 255), Color.rgb(120, 120, 220), Color.rgb(235, 145, 205))
            )
        }

        val clock = TextView(this).apply {
            text = "9:41"
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        root.addView(clock, LinearLayout.LayoutParams(-1, 52))

        val grid = GridLayout(this).apply {
            columnCount = 4
            rowCount = 3
            alignmentMode = GridLayout.ALIGN_BOUNDS
            useDefaultMargins = true
        }
        val apps = arrayOf(
            "📞\nTelefon", "💬\nÜzenetek", "📷\nKamera", "🖼️\nFotók",
            "🎵\nZene", "🗺️\nTérképek", "☀️\nIdőjárás", "⏰\nÓra",
            "📝\nJegyzetek", "📅\nNaptár", "⚙️\nBeállítások", "🛍️\nApp Store"
        )

        for (label in apps) {
            val app = TextView(this).apply {
                text = label
                textSize = 16f
                setTextColor(Color.WHITE)
                gravity = Gravity.CENTER
                setPadding(4, 10, 4, 10)
                background = GradientDrawable().apply {
                    cornerRadius = 28f
                    setColor(Color.argb(75, 255, 255, 255))
                }
                isClickable = true
                setOnClickListener { }
            }
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = 108
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
                setMargins(5, 5, 5, 5)
            }
            grid.addView(app, params)
        }
        root.addView(grid, LinearLayout.LayoutParams(-1, 0, 1f))

        val dock = LinearLayout(this).apply {
            gravity = Gravity.CENTER
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 10, 8, 10)
            background = GradientDrawable().apply {
                cornerRadius = 35f
                setColor(Color.argb(90, 255, 255, 255))
            }
        }
        arrayOf("📞", "🌐", "💬", "🎵").forEach { icon ->
            val item = TextView(this).apply {
                text = icon
                textSize = 30f
                gravity = Gravity.CENTER
            }
            dock.addView(item, LinearLayout.LayoutParams(0, 70, 1f))
        }
        root.addView(dock, LinearLayout.LayoutParams(-1, 82))

        setContentView(root)
    }
}
