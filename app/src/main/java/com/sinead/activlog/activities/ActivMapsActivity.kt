package com.sinead.activlog.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sinead.activlog.R
import com.sinead.activlog.databinding.ActivityActivMapsBinding
import com.sinead.activlog.databinding.ContentActivMapsBinding
import com.sinead.activlog.main.MainApp

class ActivMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
    private lateinit var binding: ActivityActivMapsBinding
    private lateinit var contentBinding: ContentActivMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityActivMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentActivMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
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

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        app.activs.findAll().forEach {
            val startloc = LatLng(it.lat, it.lng)
            val options = MarkerOptions()
                .title(it.time.toString() + " minute " + it.type + " start").position(startloc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(startloc, it.zoom))

            val endloc = LatLng(it.endLat, it.endLng)
            val endOptions = MarkerOptions()
                .title(it.time.toString() + " minute " + it.type + " end").position(endloc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            map.addMarker(endOptions)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(endloc, it.endZoom))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //val activ = marker.tag as ActivModel
        val tag = marker.tag as Long
        val activ = app.activs.findById(tag)
        contentBinding.currentType.text = activ!!.type
        contentBinding.currentRPE.text = "RPE: " + activ.RPE
        contentBinding.currentTime.text = activ.time.toString() + " minutes"
        contentBinding.currentDistance.text = "Distance: " + String.format("%.1f", activ.distance) + "km"

        return false
    }
}

