package com.example.attendancetime.screen.classes.subjectattendance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentAttendanceItemBinding
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.google.android.material.bottomnavigation.BottomNavigationView

class AttendanceItemFragment : Fragment() {

    private lateinit var binding: FragmentAttendanceItemBinding
    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAttendanceItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavBar = activity?.findViewById(R.id.bottomNavigationView)!!
        bottomNavBar.visibility = View.GONE

        val position: Int = CommonValue.attendanceItemPosition.value!!
        val attendance: Attendance = CommonValue.attendanceList.value!![position]

        binding.toolbar.title = "${attendance.day} ${attendance.month} ${attendance.year}"
        binding.toolbar.setNavigationOnClickListener {
            val action = AttendanceItemFragmentDirections.actionAttendanceItemFragmentToSubjectAttendanceFragment()
            findNavController().navigate(action)
        }

        val studentList = attendance.attendance
        val studentName = arrayListOf<String>()
        val status = arrayListOf<Boolean>()
        for (key in studentList.keys) {
            studentName.add(key)
            status.add(studentList[key]!!)
        }

        val adapter = AttendanceItemAdapter(studentName, status)
        binding.studentAttendanceRecyclerview.adapter = adapter
        binding.studentAttendanceRecyclerview.layoutManager = LinearLayoutManager(binding.root.context)

    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavBar.visibility = View.VISIBLE
    }
}