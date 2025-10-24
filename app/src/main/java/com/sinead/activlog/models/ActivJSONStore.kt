package com.sinead.activlog.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import com.sinead.activlog.helpers.*
import java.util.*

val JSON_FILE = "activs.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<ActivModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ActivJSONStore(private val context: Context) : ActivStore {

    var activs = mutableListOf<ActivModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ActivModel> {
        return activs
    }

    override fun findOne(id: Long) : ActivModel? {
        var foundActiv: ActivModel? = activs.find { a -> a.id == id }
        return foundActiv
    }

    override fun create(activ: ActivModel) {
        activ.id = generateRandomId()
        activs.add(activ)
        serialize()
    }

    override fun update(activ: ActivModel) {
        var foundActiv = findOne(activ.id!!)
        if (foundActiv != null) {
            foundActiv.type = activ.type
            foundActiv.time = activ.time
            foundActiv.RPE = activ.RPE
            foundActiv.note = activ.note
        }
        serialize()
    }

    internal fun logAll() {
        activs.forEach { println("$it") }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(activs, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        activs = Gson().fromJson(jsonString, listType)
    }
}
