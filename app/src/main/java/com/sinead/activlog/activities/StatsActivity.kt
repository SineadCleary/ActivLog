package com.sinead.activlog.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sinead.activlog.R
import com.sinead.activlog.databinding.ActivityStatsBinding
import com.sinead.activlog.main.MainApp
import kotlin.math.roundToInt

class StatsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        // Calculate stats
        var numActivs = 0
        var totalDist = 0f
        var avgRPE = 0f
        var avgTime = 0f
        app.activs.findAll().forEach {
            numActivs++
            avgRPE = avgRPE + it.RPE
            avgTime = avgTime + it.time
            totalDist = totalDist + it.distance
        }
        if (numActivs >= 1) {
            avgRPE /= numActivs
            avgTime /= numActivs
        }
        else {
            avgRPE = 0f
            avgTime = 0f
        }

        // Set stats text
        binding.numActivs.text = "Logged activities: " + numActivs
        binding.totalDistance.text = "Total distance: " + String.format("%.1f", totalDist) + "km"
        binding.AvgRPE.text = "Average RPE score: " + String.format("%.1f", avgRPE)
        binding.AvgTime.text = "Average exercise time: " + avgTime.roundToInt() + " minutes"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activ, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
                overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_to_top);
            }
        }
        return super.onOptionsItemSelected(item)
    }

}