package com.sinead.activlog.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivModel(var id: Long = 0,
                      var type: String = "",
                      var time: Int = 0,
                      var RPE: Int = 5,
                      var distance: Float = 0f,
                      var note: String = "",
                      var lat : Double = 0.0,
                      var lng: Double = 0.0,
                      var zoom: Float = 0f,
                      var endLat : Double = 0.0,
                      var endLng: Double = 0.0,
                      var endZoom: Float = 0f
                      ) : Parcelable

@Parcelize
data class MyLocation(var lat: Double = 0.0,
                      var lng: Double = 0.0,
                      var zoom: Float = 0f) : Parcelable
