package com.itachi1706.abp.attribouter.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.itachi1706.abp.attribouter.Attribouter
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.fragments.AboutFragment
import com.itachi1706.abp.utils.addEdgeToEdgeFlags
import com.itachi1706.abp.utils.autoSystemUiColors
import com.itachi1706.abp.utils.bind

class AboutActivity : AppCompatActivity() {

    private val toolbar: Toolbar? by bind(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        setTheme(bundle?.getInt(Attribouter.Companion.EXTRA_THEME_RES, R.style.AttribouterTheme_DayNight) ?: R.style.AttribouterTheme_DayNight)

        setContentView(R.layout.attribouter_activity_about)
        setSupportActionBar(toolbar)

        val fragment = AboutFragment()

        if (bundle != null) fragment.arguments = bundle
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragment, fragment).commit()
        else supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()

        // handle light status/nav bar colors
        window.addEdgeToEdgeFlags()
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