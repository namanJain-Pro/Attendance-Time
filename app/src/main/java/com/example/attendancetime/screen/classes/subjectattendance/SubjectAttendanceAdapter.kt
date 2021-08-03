package com.example.attendancetime.screen.classes.subjectattendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetime.R
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.google.android.material.card.MaterialCardView

class SubjectAttendanceAdapter(
    private var attendanceList: ArrayList<Attendance>?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<SubjectAttendanceAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        attendanceList.let {
            if (it != null && it.isNotEmpty()) {
                val temp = it[position]
                val presentCount = presentCount(temp.attendance)
                val absentCount = temp.attendance.size - presentCount

                holder.date.text = "${temp.day} ${temp.month} ${temp.year}"
                holder.presentCount.text = "Present = $presentCount"
                holder.absentCount.text = "Absent = $absentCount"
            }
        }
    }

    override fun getItemCount() = attendanceList?.size ?: 0

    fun updateData(attendanceList: ArrayList<Attendance>) {
        this.attendanceList = attendanceList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val attendanceCardView: MaterialCardView = view.findViewById(R.id.attendance_view)
        val date: TextView = view.findViewById(R.id.attendance_date_textview)
        val presentCount: TextView = view.findViewById(R.id.present_count_textview)
        val absentCount: TextView = view.findViewById(R.id.absent_count_textview)

        init {
            attendanceCardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private fun presentCount(attendance: HashMap<String, Boolean>): Int {
        var count = 0
        for (i in attendance.keys) {
            if (attendance[i] == true) {
                count++
            }
        }
        return count
    }
}