package com.example.g1_proyectofinal.fragments.register

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.MetricType
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList


class RegisterMetricTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_register_metric_type, container, false)
        val db = FirebaseFirestore.getInstance()
        val rootView:ConstraintLayout = view.findViewById(R.id.lytRegisterMetricType)

        val txtMetricTypeDescription:EditText = view.findViewById(R.id.txtMetricTypeDescription)
        val rbCuali:RadioButton = view.findViewById(R.id.rbCualitativo)
        val txtValue1:EditText = view.findViewById(R.id.txtValue1)
        val btnRegisterMetricType:Button = view.findViewById(R.id.btnRegisterMetricType)

        val btnAddValue:TextView = view.findViewById(R.id.btnAddValue)
        var values:ArrayList<String> = ArrayList()
        var inputIds:ArrayList<Int> = ArrayList()
        inputIds.add(R.id.txtValue1)

        btnAddValue.setOnClickListener {
            var lastId:Int = inputIds.last()
            var newInput = EditText(rootView.context)
            rootView.addView(newInput)
            newInput.hint = "Valor ${inputIds.size + 1}"
            newInput.id = generateViewId()
            inputIds.add(newInput.id)
            newInput.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

            var constraintSet = ConstraintSet()
            constraintSet.clone(rootView)

            constraintSet.connect(
                newInput.id,
                ConstraintSet.TOP,
                lastId,
                ConstraintSet.BOTTOM,
                32
            )
            constraintSet.connect(
                newInput.id,
                ConstraintSet.LEFT,
                R.id.lytRegisterMetricType,
                ConstraintSet.LEFT,
                32
            )
            constraintSet.connect(
                newInput.id,
                ConstraintSet.RIGHT,
                R.id.lytRegisterMetricType,
                ConstraintSet.RIGHT,
                32
            )
            constraintSet.connect(
                R.id.btnAddValue,
                ConstraintSet.TOP,
                newInput.id,
                ConstraintSet.BOTTOM,
                24
            )

            constraintSet.applyTo(rootView)
        }

        btnRegisterMetricType.setOnClickListener {
            var description:String = txtMetricTypeDescription.text.toString()

            var type:Boolean = true
            if (rbCuali.isChecked) {
                type = false
            }

            var value1:String = txtValue1.text.toString()
            values.add(value1)
            inputIds.removeFirst()

            for (item in inputIds) {
                var input:EditText = view.findViewById(item)
                var value:String = input.text.toString()
                values.add(value)
            }

            var id:Int = -1
            db.collection("metricTypes")
                .orderBy("metricTypeId", Query.Direction.DESCENDING)
                .limit(1).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        id = document.get("metricTypeId").toString().toInt()
                        id++

                        if (description == "" || description == null) {
                            Toast.makeText(activity, "Ingresa la descripción", Toast.LENGTH_LONG).show()
                        } else {
                            var metricType:MetricType = MetricType(id, description, type, values)
                            db.collection("metricTypes")
                                .add(metricType)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(activity, "Tipo de indicador registrado con éxito", Toast.LENGTH_LONG).show()
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
}