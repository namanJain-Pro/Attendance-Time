package com.example.attendancetime.screen.classes.subjectstudentprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetime.R
import com.example.attendancetime.datamodel.dataclasses.Student

class StudentProfileAdapter(private val studentList: ArrayList<Student>?): RecyclerView.Adapter<StudentProfileAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.textview_name)
        val section: TextView = view.findViewById(R.id.textview_section)
        val rollNumber: TextView = view.findViewById(R.id.textview_roll_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        studentList.let {
            if (it != null && it.isNotEmpty()) {
                holder.name.text = it[position].name
                holder.section.text = it[position].section
                holder.rollNumber.text = "".plus(it[position].rollNumber)
            }
        }
    }

    override fun getItemCount() = studentList?.size ?: 0
}