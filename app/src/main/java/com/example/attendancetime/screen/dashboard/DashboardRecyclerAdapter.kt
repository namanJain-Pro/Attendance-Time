package com.example.attendancetime.screen.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetime.R
import com.example.attendancetime.datamodel.SubjectClass

class DashboardRecyclerAdapter(private val class_list: ArrayList<SubjectClass>?) : RecyclerView.Adapter<DashboardRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectName: TextView = view.findViewById(R.id.subject_name)
        val section: TextView = view.findViewById(R.id.section)
        val classStrength: TextView = view.findViewById(R.id.class_strength)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        class_list.let {
            if (it != null && it.isNotEmpty()) {
                holder.subjectName.text = it[position].subjectName
                holder.section.text = it[position].section
                holder.classStrength.text = "".plus(it[position].students.size)
            }
        }
    }

    override fun getItemCount(): Int {
        return class_list?.size ?: 0
    }
}