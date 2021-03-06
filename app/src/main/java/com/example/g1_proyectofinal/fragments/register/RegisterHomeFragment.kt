package com.example.g1_proyectofinal.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.g1_proyectofinal.R

class RegisterHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View = inflater.inflate(R.layout.fragment_register_home, container, false)

        var menuRegisterEmployee:Button = view.findViewById(R.id.menuRegisterEmployee)
        var menuRegisterGroup:Button = view.findViewById(R.id.menuRegisterGroup)
        var menuRegisterMetric:Button = view.findViewById(R.id.menuRegisterMetric)
        var menuRegisterMetricType:Button = view.findViewById(R.id.menuRegisterMetricType)

        menuRegisterEmployee.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_registerEmployee)
        }

        menuRegisterGroup.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_registerGroup)
        }

        menuRegisterMetric.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_registerMetric)
        }

        menuRegisterMetricType.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_registerMetricType)
        }

        return view
    }
}