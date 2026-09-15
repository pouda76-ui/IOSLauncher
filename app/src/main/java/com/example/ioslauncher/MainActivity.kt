package com.example.ioslauncher

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        )
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(12), dp(18), dp(12), dp(12))
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(65, 155, 255), Color.rgb(145, 85, 235), Color.rgb(245, 120, 180))
            )
        }

        val clock = TextView(this).apply {
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setTypeface(null, android.graphics.Typeface.BOLD)
            text = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
        }
        root.addView(clock, LinearLayout.LayoutParams(-1, dp(42)))

        val apps = arrayOf(
            arrayOf("☎️", "Telefon", "tel:") ,
            arrayOf("💬", "Üzenetek", "sms:"),
            arrayOf("📷", "Kamera", "camera:"),
            arrayOf("🖼️", "Fotók", "gallery:"),
            arrayOf("🎵", "Zene", "music:"),
            arrayOf("🗺️", "Térképek", "geo:0,0?q="),
            arrayOf("☀️", "Időjárás", "https://www.google.com/search?q=weather"),
            arrayOf("⏰", "Óra", "clock:"),
            arrayOf("📝", "Jegyzetek", "notes:"),
            arrayOf("📅", "Naptár", "calendar:"),
            arrayOf("⚙️", "Beállítások", "settings:"),
            arrayOf("🛍️", "App Store", "market:")
        )

        val grid = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        for (row in 0 until 3) {
            val line = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
            }
            for (column in 0 until 4) {
                val app = apps[row * 4 + column]
                val cell = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER
                    setPadding(dp(3), dp(4), dp(3), dp(4))
                    setOnClickListener { openApp(app[2]) }
                }
                val icon = TextView(this).apply {
                    text = app[0]
                    textSize = 34f
                    gravity = Gravity.CENTER
                    background = GradientDrawable().apply {
                        cornerRadius = dp(18).toFloat()
                        setColor(Color.argb(235, 255, 255, 255))
                    }
                }
                val label = TextView(this).apply {
                    text = app[1]
                    textSize = 11f
                    setTextColor(Color.WHITE)
                    gravity = Gravity.CENTER
                    maxLines = 1
                }
                cell.addView(icon, LinearLayout.LayoutParams(dp(68), dp(68)))
                cell.addView(label, LinearLayout.LayoutParams(-1, dp(26)))
                line.addView(cell, LinearLayout.LayoutParams(0, -1, 1f))
            }
            grid.addView(line, LinearLayout.LayoutParams(-1, 0, 1f))
        }
        root.addView(grid, LinearLayout.LayoutParams(-1, 0, 1f))

        val dock = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(5), dp(8), dp(5))
            background = GradientDrawable().apply {
                cornerRadius = dp(28).toFloat()
                setColor(Color.argb(105, 255, 255, 255))
            }
        }
        val dockApps = arrayOf("☎️" to "tel:", "🧭" to "https://www.google.com/maps", "💬" to "sms:", "🎵" to "music:")
        dockApps.forEach { (iconText, action) ->
            dock.addView(TextView(this).apply {
                text = iconText
                textSize = 29f
                gravity = Gravity.CENTER
                setOnClickListener { openApp(action) }
            }, LinearLayout.LayoutParams(0, dp(58), 1f))
        }
        root.addView(dock, LinearLayout.LayoutParams(-1, dp(70)))

        setContentView(root)
    }

    private fun openApp(action: String) {
        try {
            val intent = when {
                action == "tel:" -> Intent(Intent.ACTION_DIAL, Uri.parse("tel:"))
                action == "sms:" -> Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"))
                action == "camera:" -> Intent("android.media.action.IMAGE_CAPTURE")
                action == "geo:0,0?q=" -> Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="))
                action.startsWith("http") -> Intent(Intent.ACTION_VIEW, Uri.parse(action))
                action == "settings:" -> Intent(Settings.ACTION_SETTINGS)
                action == "market:" -> Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.chrome"))
                else -> Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER) }
            }
            startActivity(intent)
        } catch (_: Exception) {
            try { startActivity(Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER) }) } catch (_: Exception) {}
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
