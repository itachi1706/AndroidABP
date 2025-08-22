package com.itachi1706.abp.attribouter.activities

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.itachi1706.abp.attribouter.Attribouter
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.fragments.AboutFragment
import com.itachi1706.abp.utils.autoSystemUiColors
import com.itachi1706.abp.utils.bind
import com.itachi1706.abp.utils.getThemedColor

class AboutActivity : AppCompatActivity() {

    private val toolbar: Toolbar? by bind(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        setTheme(bundle?.getInt(Attribouter.Companion.EXTRA_THEME_RES, R.style.AttribouterTheme_DayNight) ?: R.style.AttribouterTheme_DayNight)

        setContentView(R.layout.attribouter_activity_about)
        setSupportActionBar(toolbar)

        // Add color
        val statusBarView = View(this)
        window.addContentView(statusBarView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        // Set the status bar color to match the theme
        statusBarView.setBackgroundColor(this.getThemedColor(R.attr.attribouter_textColorAccent))

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom)

            statusBarView.updateLayoutParams {
                height = insets.top
            }

            WindowInsetsCompat.CONSUMED
        }


        val fragment = AboutFragment()

        if (bundle != null) fragment.arguments = bundle
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragment, fragment).commit()
        else supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()

        // handle light status/nav bar colors
        window.autoSystemUiColors()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(Bundle(), outPersistentState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(Bundle())
    }
}