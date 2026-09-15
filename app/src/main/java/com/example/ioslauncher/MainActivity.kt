package com.example.ioslauncher

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(LauncherView())
    }

    private inner class LauncherView : View(this@MainActivity) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val apps = arrayOf(
            "☎️" to "Telefon", "💬" to "Üzenetek", "📷" to "Kamera", "🖼️" to "Fotók",
            "🎵" to "Zene", "🗺️" to "Térképek", "☀️" to "Időjárás", "⏰" to "Óra",
            "📝" to "Jegyzetek", "📅" to "Naptár", "⚙️" to "Beállítások", "🛍️" to "App Store"
        )
        private var downX = 0f
        private var downY = 0f

        init { isClickable = true }

        override fun onDraw(canvas: Canvas) {
            val w = width.toFloat()
            val h = height.toFloat()
            paint.shader = LinearGradient(0f, 0f, w, h, Color.rgb(65, 155, 255), Color.rgb(245, 120, 180), Shader.TileMode.CLAMP)
            canvas.drawRect(0f, 0f, w, h, paint)
            paint.shader = null
            paint.textAlign = Paint.Align.CENTER
            paint.color = Color.WHITE
            paint.textSize = 18f
            paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
            canvas.drawText(java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date()), w / 2f, 38f, paint)
            val top = 58f
            val bottom = h - 95f
            val cellW = w / 4f
            val cellH = (bottom - top) / 3f
            for (i in apps.indices) {
                val row = i / 4
                val col = i % 4
                val cx = cellW * col + cellW / 2f
                val cy = top + cellH * row + cellH * 0.40f
                val size = minOf(cellW * 0.58f, cellH * 0.48f)
                paint.color = Color.argb(235, 255, 255, 255)
                canvas.drawRoundRect(cx - size / 2f, cy - size / 2f, cx + size / 2f, cy + size / 2f, 18f, 18f, paint)
                paint.textSize = size * 0.48f
                paint.color = Color.DKGRAY
                canvas.drawText(apps[i].first, cx, cy + size * 0.17f, paint)
                paint.textSize = 11f
                paint.color = Color.WHITE
                canvas.drawText(apps[i].second, cx, cy + size / 2f + 18f, paint)
            }
            val dockTop = h - 82f
            paint.color = Color.argb(105, 255, 255, 255)
            canvas.drawRoundRect(8f, dockTop, w - 8f, h - 10f, 28f, 28f, paint)
            arrayOf("☎️", "🧭", "💬", "🎵").forEachIndexed { i, icon ->
                paint.textSize = 29f
                paint.color = Color.WHITE
                canvas.drawText(icon, w * (i + 0.5f) / 4f, dockTop + 47f, paint)
            }
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) { downX = event.x; downY = event.y; return true }
            if (event.action == MotionEvent.ACTION_UP) {
                if (kotlin.math.abs(event.x - downX) < 30f && kotlin.math.abs(event.y - downY) < 30f) {
                    val top = 58f
                    val bottom = height - 95f
                    val cellW = width / 4f
                    val cellH = (bottom - top) / 3f
                    val col = (event.x / cellW).toInt().coerceIn(0, 3)
                    val row = ((event.y - top) / cellH).toInt()
                    if (row in 0..2) openApp(row * 4 + col)
                }
                return true
            }
            return true
        }
    }

    private fun openApp(index: Int) {
        val intent = when (index) {
            0 -> Intent(Intent.ACTION_DIAL)
            1 -> Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MESSAGING)
            2 -> Intent("android.media.action.IMAGE_CAPTURE")
            3 -> Intent(Intent.ACTION_VIEW).apply { type = "image/*" }
            4 -> Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MUSIC)
            5 -> Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="))
            6 -> Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=weather"))
            7 -> Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            8 -> Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            9 -> Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR)
            10 -> Intent(Settings.ACTION_SETTINGS)
            11 -> Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.vending"))
            else -> return
        }
        try { startActivity(intent) } catch (_: Exception) {
            try { startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)) } catch (_: Exception) {}
        }
    }
}
