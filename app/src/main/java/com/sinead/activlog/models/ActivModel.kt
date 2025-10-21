package com.sinead.activlog.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivModel(var id: Long = 0,
                      var duration: String = "",
                      var type: String = "") : Parcelable
