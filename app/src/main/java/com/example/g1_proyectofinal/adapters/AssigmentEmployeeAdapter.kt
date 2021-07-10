package com.example.g1_proyectofinal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.g1_proyectofinal.R
import com.example.g1_proyectofinal.models.FlatMetric

class AssigmentEmployeeAdapter(private var flatMetricList: MutableList<FlatMetric>): RecyclerView.Adapter<AssigmentEmployeeAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val metricId: TextView = view.findViewById(R.id.tvMetricId)
        val metricName: TextView = view.findViewById(R.id.tvTitle)
        val metricType: TextView = view.findViewById(R.id.tvType)
        val rbCuantitativo: RatingBar = view.findViewById(R.id.rbCuantitativo)
        val mtvCualitativo: EditText = view.findViewById(R.id.mtlCualitativo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.fragment_assigment_employee_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = flatMetricList[position]

        holder.metricId.text = item.metricId.toString()
        holder.metricName.text = item.metricName
        holder.metricType.text = item.metricType
        if(item.metricType.contains("Cuantitativo")){
            holder.rbCuantitativo.numStars = item.metricTypeVales.size
            holder.rbCuantitativo.visibility = VISIBLE
            holder.mtvCualitativo.visibility = INVISIBLE
        }else{
            holder.rbCuantitativo.visibility = INVISIBLE
            holder.mtvCualitativo.visibility = VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return flatMetricList.size
    }


}