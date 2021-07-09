package com.example.g1_proyectofinal.models

import java.util.*

data class Group(
    val groupId: Int,
    val groupDescription: String,
    val groupStatus: Boolean,
    val docId: String? = null
){

    override fun toString(): String {
        return groupDescription
    }
}
