package com.sinead.activlog.activities

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sinead.activlog.R
import com.sinead.activlog.adapters.ActivAdapter
import com.sinead.activlog.adapters.ActivListener
import com.sinead.activlog.databinding.ActivityActivListBinding
import com.sinead.activlog.main.MainApp
import com.sinead.activlog.models.ActivModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ActivListActivity : AppCompatActivity(), ActivListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityActivListBinding
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash screen code from: https://www.geeksforgeeks.org/android/splash-screen-in-android/
        // Install splash screen and keep it visible while `keepSplash` is true
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }

        super.onCreate(savedInstanceState)
        binding = ActivityActivListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start coroutine to delay splash screen removal
        lifecycleScope.launch {
            delay(4000) // Wait 4 seconds
            keepSplash = false // Allow splash screen to be dismissed
        }

        app = application as MainApp

        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ActivAdapter(app.activs.findAll(), this)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        // Add button
        binding.newItem.setOnClickListener {
            val launcherIntent = Intent(this, ActivActivity::class.java)
            getResult.launch(launcherIntent)
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Theme
            R.id.item_theme -> {
                // bitwise "and" extracts only night mode bits (UI_MODE_NIGHT_MASK) from uiMode
                val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

                if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                    // dark mode - set to light
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    // light mode - set to dark
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
            // Map
            R.id.item_map -> {
                val launcherIntent = Intent(this, ActivMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
                overridePendingTransition(R.anim.slide_from_top,R.anim.slide_to_bottom);
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.activs.findAll().size)
            }
        }

    override fun onActivClick(activ: ActivModel) {
        val launcherIntent = Intent(this, ActivActivity::class.java)
        launcherIntent.putExtra("activ_edit", activ)
        getClickResult.launch(launcherIntent)
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            binding.recyclerView.adapter?.notifyDataSetChanged()  // refresh RecyclerView
        }
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                        notifyItemRangeChanged(0, app.activs.findAll().size)
            }
        }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }

}


