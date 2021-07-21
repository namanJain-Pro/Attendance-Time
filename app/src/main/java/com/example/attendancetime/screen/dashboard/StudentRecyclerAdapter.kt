package com.example.attendancetime.screen.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetime.R
import com.example.attendancetime.datamodel.Student

/*
Simple Recycler adapter implementation
 */

class StudentRecyclerAdapter(private val studentList: ArrayList<Student>?) :
    RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textview_name)
        val rollNumber: TextView = view.findViewById(R.id.textview_roll_number)
        val section: TextView = view.findViewById(R.id.textview_section)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        studentList.let {
            if (it != null && it.isNotEmpty()) {
                holder.name.text = it[position].name
                holder.rollNumber.text = "".plus(it[position].rollNumber)
                holder.section.text = it[position].section
            }
        }
    }

    override fun getItemCount(): Int {
        return studentList?.size ?: 0
    }


}