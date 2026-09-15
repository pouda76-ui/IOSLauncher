package com.example.ioslauncher

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
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
            setTypeface(null, Typeface.BOLD)
        }
        root.addView(clock, LinearLayout.LayoutParams(-1, 52))

        val grid = GridLayout(this).apply {
            columnCount = 4
            rowCount = 3
            alignmentMode = GridLayout.ALIGN_BOUNDS
            useDefaultMargins = true
        }

        val apps = arrayOf(
            Pair("📞\nTelefon", "phone"), Pair("💬\nÜzenetek", "message"), Pair("📷\nKamera", "camera"), Pair("🖼️\nFotók", "photo"),
            Pair("🎵\nZene", "music"), Pair("🗺️\nTérképek", "map"), Pair("☀️\nIdőjárás", "weather"), Pair("⏰\nÓra", "clock"),
            Pair("📝\nJegyzetek", "note"), Pair("📅\nNaptár", "calendar"), Pair("⚙️\nBeállítások", "settings"), Pair("🛍️\nApp Store", "store")
        )

        for ((label, key) in apps) {
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
                setOnClickListener { launchApp(key) }
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
        arrayOf(Pair("📞", "phone"), Pair("🌐", "browser"), Pair("💬", "message"), Pair("🎵", "music")).forEach { (icon, key) ->
            val item = TextView(this).apply {
                text = icon
                textSize = 30f
                gravity = Gravity.CENTER
                isClickable = true
                setOnClickListener { launchApp(key) }
            }
            dock.addView(item, LinearLayout.LayoutParams(0, 70, 1f))
        }
        root.addView(dock, LinearLayout.LayoutParams(-1, 82))

        setContentView(root)
    }

    private fun launchApp(key: String) {
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(intent, 0)
        val wanted = when (key) {
            "phone" -> listOf("phone", "dialer", "telefon")
            "message" -> listOf("message", "messaging", "messages", "üzenet")
            "camera" -> listOf("camera", "kamera")
            "photo" -> listOf("gallery", "photos", "fotók", "galéria")
            "music" -> listOf("music", "zene")
            "map" -> listOf("maps", "térkép", "google maps")
            "weather" -> listOf("weather", "időjárás")
            "clock" -> listOf("clock", "óra")
            "note" -> listOf("note", "notes", "jegyzet")
            "calendar" -> listOf("calendar", "naptár")
            "settings" -> listOf("settings", "beállítás")
            "store" -> listOf("play store", "galaxy store", "app store")
            "browser" -> listOf("chrome", "browser", "internet", "böngész")
            else -> emptyList()
        }
        val match = apps.firstOrNull { info ->
            val name = info.loadLabel(packageManager).toString().lowercase()
            name != "ioslauncher" && wanted.any { name.contains(it) }
        }
        if (match != null) {
            try {
                startActivity(match.activityInfo.packageName.let { packageManager.getLaunchIntentForPackage(it) })
            } catch (_: Exception) { }
        }
    }
}
