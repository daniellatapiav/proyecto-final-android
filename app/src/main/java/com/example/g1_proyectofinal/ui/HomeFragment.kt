package com.example.g1_proyectofinal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.g1_proyectofinal.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View = inflater.inflate(R.layout.fragment_home, container, false)

        var titleHello:TextView = view.findViewById(R.id.titleName)
        var menuRegister:Button = view.findViewById(R.id.menuRegister)
        var menuAssign:Button = view.findViewById(R.id.menuAssign)
        var menuReport:Button = view.findViewById(R.id.menuReport)


        // TODO: Cambiar de fragment/activity
        val userName:String = "Nombre"
        titleHello.text = "Hola, ${userName}"

        menuRegister.setOnClickListener {
            // TODO: Cambiar de fragment/activity
        }

        menuAssign.setOnClickListener {
            // TODO: Cambiar de fragment/activity
        }

        menuReport.setOnClickListener {
            // TODO: Cambiar de fragment/activity
        }

        return view
    }
}