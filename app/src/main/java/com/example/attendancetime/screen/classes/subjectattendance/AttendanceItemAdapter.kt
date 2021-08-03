package com.example.attendancetime.screen.classes.subjectattendance

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetime.R

class AttendanceItemAdapter(
    private val studentNameList: ArrayList<String>,
    private val statusList: ArrayList<Boolean>
) : RecyclerView.Adapter<AttendanceItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.student_name)
        val status: TextView = view.findViewById(R.id.student_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance_list_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = studentNameList[position]
        holder.status.text = "${statusList[position]}"
        if (statusList[position]) {
            holder.status.setTextColor(Color.parseColor("#128cff"))
        } else {
            holder.status.setTextColor(Color.parseColor("#ff3112"))
        }
    }

    override fun getItemCount() = studentNameList.size
}