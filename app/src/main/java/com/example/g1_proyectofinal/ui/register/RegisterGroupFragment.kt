package com.example.g1_proyectofinal.ui.register

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.Employee
import com.example.g1_proyectofinal.models.Group
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RegisterGroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_register_group, container, false)
        val db = FirebaseFirestore.getInstance()

        val txtGroupDescription:EditText = view.findViewById(R.id.txtGroupDescription)

        val btnRegisterGroup:Button = view.findViewById(R.id.btnRegisterGroup)
        val contextView:View = view.findViewById(R.id.lytRegisterGroup)

        btnRegisterGroup.setOnClickListener {
            var description:String = txtGroupDescription.text.toString()
            var id:Int = -1

            db.collection("groups")
            .orderBy("groupId", Query.Direction.DESCENDING)
            .limit(1).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    id = document.get("groupId").toString().toInt()
                    id++

                    if (description == "" || description == null) {
                        Toast.makeText(activity, "Ingresa la descripción", Toast.LENGTH_LONG).show()
                    } else {
                        var group:Group = Group(id, description, true)
                        db.collection("groups")
                            .add(group)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(activity, "Grupo registrado con éxito", Toast.LENGTH_LONG).show()
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