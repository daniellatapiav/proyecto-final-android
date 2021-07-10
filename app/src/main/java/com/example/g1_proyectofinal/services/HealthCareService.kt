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
        EmployeeHandler.employeeList = mutableListOf()

        getMetrics(db)
        getMetricTypes(db)
        getGroups(db)
        getEmployees(db)
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

    private fun getGroups(db:FirebaseFirestore) {
        db.collection("groups").orderBy("groupId")
            .get()
            .addOnSuccessListener { result ->
                GroupHandler.groupList.addAll(result.map { doc ->
                    Group(
                        doc.get("groupId").toString().toInt(),
                        doc.get("groupDescription") as String,
                        doc.get("groupStatus").toString().toBoolean(),
                        doc.get("groupMetrics") as ArrayList<Int>,
                        doc.id)
                    })

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting groups: ", exception)
            }
    }

    private fun getEmployees(db:FirebaseFirestore) {
        db.collection("employees")
            .get()
            .addOnSuccessListener { result ->
                EmployeeHandler.employeeList.addAll(result.map { doc ->
                    Employee(
                        doc.get("contractNumber")?.toString(),
                        doc.get("docType") as String,
                        doc.get("docNumber") as String,
                        doc.get("names")?.toString(),
                        doc.get("firstSurname") as String,
                        doc.get("secondSurname") as String,
                        doc.get("email") as String,
                        doc.get("employeeGroup").toString().toInt(),
                        doc.get("employeeStatus").toString().toBoolean(),
                        null,
                        doc.id)
                })

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting employees: ", exception)
            }
    }

    fun getFlatMetrics(){
        FlatMetricHandler.flatMetricList.addAll(
            FlatMetricHandler.metricList.map { m ->
                val metricTypes = FlatMetricHandler.metricTypeList.first { x -> x.metricTypeId == m.metricType }
                FlatMetric(
                    m.metricId,
                    m.metricDescription,
                    metricTypes.metricTypeDescription,
                    metricTypes.metricTypeValues,
                    false)
            } )
    }

    fun getMetricByGroup(groupId: Int): MutableList<FlatMetric> {
        val metricIds = GroupHandler.groupList.first { f -> f.groupId == groupId }.groupMetrics.map { it as Int }
        var list: MutableList<FlatMetric> = mutableListOf()
        Log.d("SIZE", metricIds.size.toString())

        if (metricIds != null) {
            for(ID in metricIds){
                Log.d("xx for: ",  ID.toString())
                if(ID != 0)
                    list.add(FlatMetricHandler.flatMetricList.first { x -> x.metricId == ID })
            }

        }



        return list
    }

}