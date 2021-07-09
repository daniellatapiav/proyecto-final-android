package com.example.g1_proyectofinal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.FlatMetric


class AssigmentGroupAdapter(private var flatMetricList: MutableList<FlatMetric>): Adapter<AssigmentGroupAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val metricId: TextView = view.findViewById(R.id.tvMetricId)
        val metricName: TextView = view.findViewById(R.id.tvTitle)
        val metricType: TextView = view.findViewById(R.id.tvType)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.fragment_assigment_group_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = flatMetricList[position]

        holder.metricId.text = item.metricId.toString()
        holder.metricName.text = item.metricName
        holder.metricType.text = item.metricType
    }

    override fun getItemCount(): Int {
        return flatMetricList.size
    }


}