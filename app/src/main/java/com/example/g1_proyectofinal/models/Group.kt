package com.example.g1_proyectofinal.models

import kotlin.collections.ArrayList

data class Group(
    val groupId: Int,
    val groupDescription: String,
    val groupStatus: Boolean,
    val groupMetrics: ArrayList<Int>,
    val docId: String? = null
){

    override fun toString(): String {
        return groupDescription
    }
}
