package com.example.attendancetime.screen.classes.subjectattendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetime.R
import com.example.attendancetime.datamodel.dataclasses.Student

class StudentAttendanceAdapter(private val studentList: ArrayList<Student>?) :
    RecyclerView.Adapter<StudentAttendanceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.student_name)
        val rollNumber: TextView = view.findViewById(R.id.student_rollNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student_attendance, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        studentList.let {
            if (it != null && it.isNotEmpty()) {
                holder.name.text = it[position].name
                holder.rollNumber.text = it[position].rollNumber.toString()
            }
        }
    }

    override fun getItemCount() = studentList?.size ?: 0


}