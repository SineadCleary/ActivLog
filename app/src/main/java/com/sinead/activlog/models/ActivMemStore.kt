package com.sinead.activlog.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ActivMemStore : ActivStore {
    val activs = ArrayList<ActivModel>()

    override fun findAll(): List<ActivModel> {
        return activs
    }

    override fun findOne(id: Long): ActivModel? {
        var foundActiv: ActivModel? = activs.find { a -> a.id == id }
        return foundActiv
    }

    override fun create(activ: ActivModel) {
        activ.id = getId()
        activs.add(activ)
        logAll()
    }

    override fun update(activ: ActivModel) {
        var foundActiv: ActivModel? = activs.find { a -> a.id == activ.id }
        if (foundActiv != null) {
            foundActiv.type = activ.type
            foundActiv.time = activ.time
            foundActiv.RPE = activ.RPE
            foundActiv.note = activ.note
            foundActiv.lat = activ.lat
            foundActiv.lng = activ.lng
            foundActiv.zoom = activ.zoom
            foundActiv.endLat = activ.endLat
            foundActiv.endLng = activ.endLng
            foundActiv.endZoom = activ.endZoom
            logAll()
        }
    }

    override fun delete(activ: ActivModel) {
        activs.remove(activ)
    }

    fun logAll() {
        activs.forEach { i("$it") }
    }

    override fun findById(id:Long) : ActivModel? {
        val foundPlacemark: ActivModel? = activs.find { it.id == id }
        return foundPlacemark
    }

}