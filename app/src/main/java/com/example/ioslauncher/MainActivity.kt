package com.example.ioslauncher

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.rgb(28, 28, 30)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            setPadding(18, 42, 18, 18)
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(80, 170, 255), Color.rgb(145, 90, 235), Color.rgb(245, 120, 180))
            )
        }

        val time = TextView(this).apply {
            text = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            textSize = 17f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        root.addView(time, LinearLayout.LayoutParams(-1, 42))

        val grid = GridLayout(this).apply {
            columnCount = 4
            rowCount = 3
            useDefaultMargins = false
            alignmentMode = GridLayout.ALIGN_BOUNDS
        }
        val apps = arrayOf(
            "☎️\nTelefon", "💬\nÜzenetek", "📷\nKamera", "🖼️\nFotók",
            "🎵\nZene", "🗺️\nTérképek", "☀️\nIdőjárás", "⏰\nÓra",
            "📝\nJegyzetek", "📅\nNaptár", "⚙️\nBeállítások", "🛍️\nApp Store"
        )
        apps.forEach { addApp(grid, it) }
        root.addView(grid, LinearLayout.LayoutParams(-1, 0, 1f))

        val dock = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(8, 10, 8, 10)
            background = GradientDrawable().apply {
                cornerRadius = 34f
                setColor(Color.argb(95, 255, 255, 255))
            }
        }
        arrayOf("☎️", "🧭", "💬", "🎵").forEach { icon ->
            val t = TextView(this).apply {
                text = icon
                textSize = 30f
                gravity = Gravity.CENTER
            }
            dock.addView(t, LinearLayout.LayoutParams(0, 64, 1f))
        }
        root.addView(dock, LinearLayout.LayoutParams(-1, 84))
        setContentView(root)
    }

    private fun addApp(grid: GridLayout, label: String) {
        val parts = label.split("\n")
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(4, 8, 4, 8)
        }
        val icon = TextView(this).apply {
            text = parts[0]
            textSize = 36f
            gravity = Gravity.CENTER
            background = GradientDrawable().apply {
                cornerRadius = 20f
                setColor(Color.argb(220, 255, 255, 255))
            }
        }
        val name = TextView(this).apply {
            text = parts[1]
            textSize = 11f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 5, 0, 0)
        }
        box.addView(icon, LinearLayout.LayoutParams(68, 68))
        box.addView(name, LinearLayout.LayoutParams(-1, 32))
        grid.addView(box, GridLayout.LayoutParams().apply {
            width = 0
            height = GridLayout.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        })
    }
}
