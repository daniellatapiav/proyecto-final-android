package com.example.g1_proyectofinal.models

data class Employee(
    val contractNumber: String,
    val docType: String,
    val docNumber: String,
    val names: String,
    val firstSurname: String,
    val secondSurname: String,
    val email: String,
    val employeeGroup: Int,
    val employeeStatus: Boolean,
)
