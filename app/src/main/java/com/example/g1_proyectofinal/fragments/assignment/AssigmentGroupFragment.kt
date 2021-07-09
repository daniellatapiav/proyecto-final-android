package com.example.g1_proyectofinal.fragments.assignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.iterator
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.adapters.AssigmentGroupAdapter
import com.example.g1_proyectofinal.models.FlatMetric
import com.example.g1_proyectofinal.models.FlatMetricHandler
import com.example.g1_proyectofinal.models.Group
import com.example.g1_proyectofinal.models.GroupHandler
import com.example.g1_proyectofinal.services.HealthCareService
import com.google.firebase.firestore.FirebaseFirestore


class AssigmentGroupFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_assigment_group, container, false)
        val rvMetrics: RecyclerView = view.findViewById(R.id.rvIndicators)
        val spinnerGroup: Spinner = view.findViewById(R.id.spinnerGroup)
        val btnAssign: Button = view.findViewById(R.id.btnAsignar)
        val db = FirebaseFirestore.getInstance()

        spinnerGroup.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, GroupHandler.groupList)
        var group: Group? = null

        spinnerGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                group = parent?.getItemAtPosition(position) as Group?
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        rvMetrics.adapter = AssigmentGroupAdapter(FlatMetricHandler.flatMetricList)
        rvMetrics.layoutManager = LinearLayoutManager(requireContext())

        btnAssign.setOnClickListener {

            val metricIds: MutableList<Int> = mutableListOf()
            Log.d("ID: ",group?.docId.toString())

            val dbGroupRef = db.collection("groups").document(group?.docId.toString())

            db.runTransaction { transaction ->
                //val snapshot = transaction.get(dbGroupRef)

               for(item in rvMetrics.iterator())
               {
                   val metricId: TextView = item.findViewById(R.id.tvMetricId)
                   val title: TextView = item.findViewById(R.id.tvTitle)
                   val checkbox: CheckBox = item.findViewById(R.id.checkBox)

                   if(checkbox.isChecked)
                   {
                       Log.d("Metric ID Selected: ", metricId.text.toString())
                       Log.d("Title Selected: ", title.text.toString())

                       metricIds.add(metricId.text.toString().toInt())
                   }

               }

                transaction.update(dbGroupRef, "groupMetrics", metricIds)

            }

        }

        return view
    }



}