package com.example.g1_proyectofinal.models

data class Metric(
    val metricId: Int,
    val metricDescription: String,
    val metricType: Int,
    val formula: String,
    val metricStatus: Boolean
)
