package com.example.g1_proyectofinal.fragments.register

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.Metric
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class RegisterMetricFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_register_metric, container, false)
        val db = FirebaseFirestore.getInstance()

        val txtMetricDescription:EditText = view.findViewById(R.id.txtMetricDescription)
        val txtFormula:EditText = view.findViewById(R.id.txtFormula)

        val spinnerMetricTypes:Spinner = view.findViewById(R.id.spinnerMetricTypes)
        fillMetricTypes(db, spinnerMetricTypes)
        var typeName:String = ""

        spinnerMetricTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                typeName = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        val btnRegisterMetric:Button = view.findViewById(R.id.btnRegisterMetric)

        btnRegisterMetric.setOnClickListener {
            var description:String = txtMetricDescription.text.toString()
            var formula:String = txtFormula.text.toString()

            var id:Int = -1
            var type:Int = -1

            db.collection("metrics")
                .whereEqualTo("metricDescription", typeName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        type = document.get("metricId").toString().toInt()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }

            db.collection("metrics")
                .orderBy("metricId", Query.Direction.DESCENDING)
                .limit(1).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        id = document.get("metricId").toString().toInt()
                        id++

                        if (description == "" || description == null) {
                            Toast.makeText(activity, "Ingresa la descripción", Toast.LENGTH_LONG).show()
                        } else if (formula == "" || formula == null) {
                            Toast.makeText(activity, "Ingresa la fórmula", Toast.LENGTH_LONG).show()
                        } else {
                            var metric:Metric = Metric(id, description, type, formula, true)
                            db.collection("metrics")
                                .add(metric)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(activity, "Indicador registrado con éxito", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(activity, "Sucedió un error", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                    Toast.makeText(activity, "Error al generar el ID", Toast.LENGTH_LONG).show()
                }
        }

        return view
    }

    private fun fillMetricTypes(db:FirebaseFirestore, spinner:Spinner) {
        var list = mutableListOf<String>()

        db.collection("metrics")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document.get("metricDescription").toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        )

        spinner.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}