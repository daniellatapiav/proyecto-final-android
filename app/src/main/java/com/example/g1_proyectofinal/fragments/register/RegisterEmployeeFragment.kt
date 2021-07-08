package com.example.g1_proyectofinal.fragments.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.Employee
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class RegisterEmployeeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_register_employee, container, false)
        val db = FirebaseFirestore.getInstance()

        val txtDocNumber:EditText = view.findViewById(R.id.txtDocNumber)
        val txtNames:EditText = view.findViewById(R.id.txtNames)
        val txtFirstSurname:EditText = view.findViewById(R.id.txtFirstSurname)
        val txtSecondSurname:EditText = view.findViewById(R.id.txtSecondSurname)
        val txtEmail:EditText = view.findViewById(R.id.txtEmail)

        val spinnerDocTypes:Spinner = view.findViewById(R.id.spinnerDocTypes)
        fillDocTypes(spinnerDocTypes)
        var docType = ""

        spinnerDocTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                docType = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val rgGroup:RadioGroup = view.findViewById(R.id.rgGroup)
        fillGroups(db, rgGroup)

        val btnRegisterEmployee:Button = view.findViewById(R.id.btnRegisterEmployee)
        val contextView:View = view.findViewById(R.id.lytRegisterEmployee)

        btnRegisterEmployee.setOnClickListener {
            var docNumber:String = txtDocNumber.text.toString()
            var names:String = txtNames.text.toString()
            var firstSurname:String = txtFirstSurname.text.toString()
            var secondSurname:String = txtSecondSurname.text.toString()
            var email:String = txtEmail.text.toString()
            var group:Int = rgGroup.checkedRadioButtonId
            var contractNumber:String = UUID.randomUUID().toString()


            if (docType == "Tipo") {
                Snackbar.make(contextView, "Selecciona el tipo de documento", Snackbar.LENGTH_LONG).show()
            } else if (docNumber == "" || docNumber == null) {
                Snackbar.make(contextView, "Ingresa el número de documento", Snackbar.LENGTH_LONG).show()
            } else if (names == "" || names == null) {
                Snackbar.make(contextView, "Ingresa los nombres", Snackbar.LENGTH_LONG).show()
            } else if (firstSurname == "" || firstSurname == null) {
                Snackbar.make(contextView, "Ingresa el apellido paterno", Snackbar.LENGTH_LONG).show()
            } else if (secondSurname == "" || secondSurname == null) {
                Snackbar.make(contextView, "Ingresa el apellido materno", Snackbar.LENGTH_LONG).show()
            } else if (email == "" || email == null) {
                Snackbar.make(contextView, "Ingresa el correo electrónico", Snackbar.LENGTH_LONG).show()
            } else if (group == -1) {
                Snackbar.make(contextView, "Selecciona el grupo", Snackbar.LENGTH_LONG).show()
            } else {
                var employee:Employee = Employee(contractNumber, docType, docNumber, names, firstSurname, secondSurname, email, group, true)

                db.collection("employees")
                    .add(employee)
                    .addOnSuccessListener { documentReference ->
                        Snackbar.make(contextView, "Empleado registrado con éxito", Snackbar.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e ->
                        Snackbar.make(contextView, "Sucedió un error", Snackbar.LENGTH_LONG).show()
                    }
            }

        }

        return view
    }

    private fun fillDocTypes(spinner:Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tipos_documento,
            android.R.layout.simple_spinner_item
        ).also{
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun fillGroups(db:FirebaseFirestore, radioGroup: RadioGroup) {
        db.collection("groups").orderBy("groupId")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var radioButton:RadioButton = RadioButton(requireContext())
                    radioButton.apply {
                        text = document.get("groupDescription").toString()
                        id = document.get("groupId").toString().toInt()
                    }
                    radioGroup.addView(radioButton)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}