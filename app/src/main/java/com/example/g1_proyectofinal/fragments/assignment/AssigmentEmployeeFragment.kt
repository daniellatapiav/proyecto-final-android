package com.example.g1_proyectofinal.fragments.assignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.adapters.AssigmentEmployeeAdapter
import com.example.g1_proyectofinal.adapters.AssigmentGroupAdapter
import com.example.g1_proyectofinal.models.*
import com.example.g1_proyectofinal.services.HealthCareService
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class AssigmentEmployeeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_assigment_employee, container, false)
        val rvMetrics: RecyclerView = view.findViewById(R.id.rvIndicatorsForEmployee)
        val spinnerEmployee: Spinner = view.findViewById(R.id.spinnerEmployee)
        val btnEvaluar: Button = view.findViewById(R.id.btnEvaluar)
        val db = FirebaseFirestore.getInstance()

        spinnerEmployee.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, EmployeeHandler.employeeList)
        var employee: Employee? = null
        var flatMetricList: MutableList<FlatMetric> = mutableListOf()
        spinnerEmployee.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                employee = parent?.getItemAtPosition(position) as Employee?
                flatMetricList = employee?.let { HealthCareService.getMetricByGroup(it.employeeGroup) }!!
                rvMetrics.adapter = AssigmentEmployeeAdapter(flatMetricList)
                rvMetrics.adapter?.notifyDataSetChanged()


            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        rvMetrics.adapter = AssigmentEmployeeAdapter(flatMetricList)
        rvMetrics.layoutManager = LinearLayoutManager(requireContext())


        btnEvaluar.setOnClickListener {

            val dbEmployeeRef = db.collection("employees").document(employee?.docId.toString())


            db.runTransaction { transaction ->

                var assessmentArrayMap: MutableList<Assessment> = mutableListOf()
                rvMetrics.Recycler()
                Log.d("Employee ID: ", employee?.docId.toString())
                Log.d("SIZE: ", rvMetrics.childCount.toString())
                for(item in rvMetrics.iterator())
                {
                    Log.d("1: ", rvMetrics.childCount.toString())
                    var assessment: Map<String,Any> = mapOf()

                    val metricId: TextView = item.findViewById(R.id.tvMetricId)
                    val metricDescription: TextView = item.findViewById(R.id.tvTitle)
                    val metricType: TextView = item.findViewById(R.id.tvType)
                    var value = ""

                    Log.d("2: ", rvMetrics.childCount.toString())

                    if(metricType.text.contains("Cuantitativo")){
                        val stars: RatingBar = item.findViewById(R.id.rbCuantitativo)
                        value = stars.rating.toString()
                    }else{
                        val mtvCualitativo: EditText = view.findViewById(R.id.mtlCualitativo)
                        value = mtvCualitativo.text.toString()
                    }

                    Log.d("metricId: ", metricId.text.toString())
                    Log.d("metricDescription: ", metricDescription.text.toString())
                    Log.d("metricType: ", metricType.text.toString())
                    Log.d("value: ", value.toString())

                    assessmentArrayMap.add(Assessment( metricId.text.toString().toInt(), metricDescription.text.toString(), metricType.text.toString(), value))

                    /*
                    assessment.plus(metricId.text.toString().toInt() to "metricId")
                    assessment.plus(metricDescription.text.toString() to "metricDescription")
                    assessment.plus(metricType.text.toString() to "metricType")
                    assessment.plus(value to "value")


                    assessment.plus("metricId" to metricId.text.toString().toInt())
                    assessment.plus("metricDescription" to metricDescription.text.toString() )
                    assessment.plus( "metricType" to metricType.text.toString())
                    assessment.plus("value" to value )
                    */

                }

                transaction.update(dbEmployeeRef, "assessment", assessmentArrayMap)
            }

        }
        return view
    }
}