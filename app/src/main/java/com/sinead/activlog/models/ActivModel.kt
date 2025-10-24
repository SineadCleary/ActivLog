package com.sinead.activlog.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivModel(var id: Long = 0,
                      var type: String = "",
                      var time: Int = 0,
                      var RPE: Int = 5,
                      var note: String = ""
                      ) : Parcelable
