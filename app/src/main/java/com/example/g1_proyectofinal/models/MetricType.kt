package com.example.g1_proyectofinal.models

data class MetricType(
    val metricTypeId: Int,
    val metricTypeDescription: String,
    val metricType: Boolean,
    val metricTypeValues: ArrayList<Any>
)
