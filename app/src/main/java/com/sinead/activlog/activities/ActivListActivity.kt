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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinead.activlog.R
import com.sinead.activlog.adapters.ActivAdapter
import com.sinead.activlog.adapters.ActivListener
import com.sinead.activlog.databinding.ActivityActivListBinding
import com.sinead.activlog.main.MainApp
import com.sinead.activlog.models.ActivModel

class ActivListActivity : AppCompatActivity(), ActivListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityActivListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ActivAdapter(app.activs.findAll(), this)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Add
            R.id.item_add -> {
                val launcherIntent = Intent(this, ActivActivity::class.java)
                getResult.launch(launcherIntent)
            }
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
}


