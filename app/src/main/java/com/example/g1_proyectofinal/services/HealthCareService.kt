package com.example.g1_proyectofinal.services

import android.content.ContentValues

import android.util.Log
import com.example.g1_proyectofinal.models.*
import com.google.firebase.firestore.FirebaseFirestore

object HealthCareService {

    fun init() {
        val db = FirebaseFirestore.getInstance()

        FlatMetricHandler.flatMetricList = mutableListOf()
        FlatMetricHandler.metricList = mutableListOf()
        FlatMetricHandler.metricTypeList = mutableListOf()
        GroupHandler.groupList = mutableListOf()

        getMetrics(db)
        getMetricTypes(db)
        getGroups(db)
    }

    private fun getMetricTypes(db:FirebaseFirestore){
        db.collection("metricTypes").orderBy("metricTypeId")
            .get()
            .addOnSuccessListener { it ->
                FlatMetricHandler.metricTypeList.addAll(it.map { doc ->
                    MetricType(doc.get("metricTypeId").toString().toInt(),
                        doc.get("metricTypeDescription") as String,
                        doc.get("metricType") as Boolean,
                        doc.get("metricTypeValues") as ArrayList<String>)
                })

            }.addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting metric types: ", exception)
            }

    }

    private fun getMetrics(db:FirebaseFirestore){
        db.collection("metrics").orderBy("metricId")
            .get()
            .addOnSuccessListener { result ->
                FlatMetricHandler.metricList.addAll(result.map { doc ->
                    Metric(
                        doc.get("metricId").toString().toInt(),
                        doc.get("metricDescription") as String,
                        doc.get("metricType").toString().toInt(),
                        doc.get("formula") as String,
                        doc.get("metricStatus").toString().toBoolean())
                })


            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting metrics: ", exception)
            }
    }

    fun getFlatMetrics(){
        FlatMetricHandler.flatMetricList.addAll(
            FlatMetricHandler.metricList.map { m -> FlatMetric(
                m.metricId,
                m.metricDescription,
                FlatMetricHandler.metricTypeList.first { x -> x.metricTypeId == m.metricType }.metricTypeDescription,
                false)
            } )
    }

    private fun getGroups(db:FirebaseFirestore) {
        db.collection("groups").orderBy("groupId")
            .get()
            .addOnSuccessListener { result ->
                GroupHandler.groupList.addAll(result.map { doc ->
                    Group(
                        doc.get("groupId").toString().toInt(),
                        doc.get("groupDescription") as String,
                        doc.get("groupStatus").toString().toBoolean(),
                        doc.id)
                    })

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting groups: ", exception)
            }
    }

}