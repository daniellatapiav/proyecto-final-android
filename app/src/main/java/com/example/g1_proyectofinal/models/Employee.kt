package com.example.g1_proyectofinal.models

data class Employee(
    val contractNumber: String? = null,
    val docType: String,
    val docNumber: String,
    val names: String? = null,
    val firstSurname: String,
    val secondSurname: String,
    val email: String,
    val employeeGroup: Int,
    val employeeStatus: Boolean,
    val assessment: MutableList<Assessment>? = null,
    val docId: String? = null
) {

    override fun toString(): String {
        return "$firstSurname $secondSurname"
    }
}
