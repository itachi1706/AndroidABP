package me.jfenn.attriboutersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itachi1706.abp.attribouter.attribouterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // immediately start the Attribouter activity
        attribouterActivity {
            withFile(R.xml.about)
            withTheme(com.itachi1706.abp.attribouter.R.style.AttribouterTheme_DayNight)
            withGitHubToken(BuildConfig.GITHUB_TOKEN)
        }
    }

}