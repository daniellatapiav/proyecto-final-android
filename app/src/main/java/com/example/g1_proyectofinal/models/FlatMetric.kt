package com.example.g1_proyectofinal.models

data class FlatMetric(
        val metricId: Int,
        val metricName: String,
        val metricType: String,
        val metricTypeVales: ArrayList<String>,
        val metricSelected: Boolean
)
