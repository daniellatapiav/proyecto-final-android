package com.example.g1_proyectofinal.fragments.assignment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.*
import com.example.g1_proyectofinal.services.HealthCareService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore


class AssigmentHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HealthCareService.init()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_assigment_home, container, false)
        val btnAssignGroup: Button = view.findViewById(R.id.btnAssignGroup)

        btnAssignGroup.setOnClickListener {

            HealthCareService.getFlatMetrics()
            Navigation.findNavController(view).navigate(R.id.nav_assigmentGroup)

        }

        return view
    }


}
