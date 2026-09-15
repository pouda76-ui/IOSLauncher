package com.example.ioslauncher

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            setPadding(12, 36, 12, 12)
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(70, 160, 255), Color.rgb(150, 90, 235), Color.rgb(245, 120, 180))
            )
        }

        val clock = TextView(this).apply {
            text = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        root.addView(clock, LinearLayout.LayoutParams(-1, 48))

        val apps = arrayOf(
            "☎️\nTelefon", "💬\nÜzenetek", "📷\nKamera", "🖼️\nFotók",
            "🎵\nZene", "🗺️\nTérképek", "☀️\nIdőjárás", "⏰\nÓra",
            "📝\nJegyzetek", "📅\nNaptár", "⚙️\nBeállítások", "🛍️\nApp Store"
        )

        for (row in 0 until 3) {
            val line = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
            }
            for (column in 0 until 4) {
                val item = apps[row * 4 + column].split("\n")
                val cell = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER
                }
                val icon = TextView(this).apply {
                    text = item[0]
                    textSize = 32f
                    gravity = Gravity.CENTER
                    background = GradientDrawable().apply {
                        cornerRadius = 18f
                        setColor(Color.WHITE)
                    }
                }
                val label = TextView(this).apply {
                    text = item[1]
                    textSize = 10f
                    setTextColor(Color.WHITE)
                    gravity = Gravity.CENTER
                }
                cell.addView(icon, LinearLayout.LayoutParams(64, 64))
                cell.addView(label, LinearLayout.LayoutParams(0, 30, 1f))
                line.addView(cell, LinearLayout.LayoutParams(0, 112, 1f))
            }
            root.addView(line, LinearLayout.LayoutParams(-1, 112))
        }

        val dock = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            background = GradientDrawable().apply {
                cornerRadius = 28f
                setColor(Color.argb(100, 255, 255, 255))
            }
        }
        arrayOf("☎️", "🧭", "💬", "🎵").forEach { icon ->
            dock.addView(TextView(this).apply {
                text = icon
                textSize = 28f
                gravity = Gravity.CENTER
            }, LinearLayout.LayoutParams(0, 60, 1f))
        }
        root.addView(dock, LinearLayout.LayoutParams(-1, 76))

        setContentView(root)
    }
}
