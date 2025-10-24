package com.sinead.activlog.models

interface ActivStore {
    fun findAll(): List<ActivModel>
    fun create(activ: ActivModel)
    fun findOne(id: Long) : ActivModel?
    fun update(activ: ActivModel)
    fun delete(avtiv: ActivModel)
}