package com.sinead.activlog.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sinead.activlog.R
import com.sinead.activlog.databinding.ActivityMapBinding
import com.sinead.activlog.models.MyLocation

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    // Locations
    private var location = MyLocation()
    private var endLocation = MyLocation()
    // Markers
    private lateinit var startMarker: Marker
    private lateinit var endMarker: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //location = intent.extras?.getParcelable("location",Location::class.java)!!
        location = intent.extras?.getParcelable<MyLocation>("location")!!
        endLocation = intent.extras?.getParcelable<MyLocation>("endLocation")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Confirm location with button
        binding.btnConfirm.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("location", location)
            resultIntent.putExtra("endLocation", endLocation)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
            overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_to_top);
        }

        // Confirm location pressing back
        onBackPressedDispatcher.addCallback(this ) {
            val resultIntent = Intent()
            resultIntent.putExtra("location", location)
            resultIntent.putExtra("endLocation", endLocation)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
            overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_to_top);
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val endLoc = LatLng(endLocation.lat, endLocation.lng)
        val optionsStart = MarkerOptions()
            .title("Start")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        val optionsEnd = MarkerOptions()
            .title("End")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            .snippet("GPS : $endLoc")
            .draggable(true)
            .position(endLoc)
        startMarker = map.addMarker(optionsStart)!!
        endMarker = map.addMarker(optionsEnd)!!
        map.setOnMarkerDragListener(this)
        map.setOnMarkerClickListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(p0: Marker) {  }

    override fun onMarkerDragEnd(marker: Marker) {
        if (marker == startMarker) {
            location.lat = marker.position.latitude
            location.lng = marker.position.longitude
            location.zoom = map.cameraPosition.zoom
        }
        else if (marker == endMarker) {
            endLocation.lat = marker.position.latitude
            endLocation.lng = marker.position.longitude
            endLocation.zoom = map.cameraPosition.zoom
        }

    }

    override fun onMarkerDragStart(p0: Marker) {  }

    override fun onMarkerClick(marker: Marker): Boolean {
        val pos = marker.position
        marker.snippet = "GPS: ${pos.latitude}, ${pos.longitude}"
        return false
    }
}
