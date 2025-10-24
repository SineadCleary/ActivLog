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
            logAll()
        }
    }

    fun logAll() {
        activs.forEach { i("$it") }
    }

}