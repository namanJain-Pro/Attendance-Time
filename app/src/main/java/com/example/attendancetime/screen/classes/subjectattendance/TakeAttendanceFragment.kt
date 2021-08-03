package com.example.attendancetime.screen.classes.subjectattendance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.bluetoothlogic.DiscoverDevices
import com.example.attendancetime.bluetoothlogic.IdentifyDevices
import com.example.attendancetime.databinding.FragmentTakeAttendanceBinding
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.firestoreDB.FireStoreDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TakeAttendanceFragment : Fragment() {


    private lateinit var binding: FragmentTakeAttendanceBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var studentList: ArrayList<Student>
    private lateinit var btObject: DiscoverDevices
    private lateinit var identifyDevices: IdentifyDevices

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTakeAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        bottomNav = activity?.findViewById(R.id.bottomNavigationView)!!
        bottomNav.visibility = View.GONE

        binding.dateTextview.text =
            SimpleDateFormat("dd LLLL yyyy").format(Calendar.getInstance().time).toString()

        studentList = CommonValue.presentStudentList.value!!

        btObject = DiscoverDevices(binding.root.context)
        identifyDevices = IdentifyDevices()

        binding.takeAttendanceBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            btObject.discoverDevices()
        }

        CommonValue.btDeviceList.observe(viewLifecycleOwner, {
            identifyDevices.validatePresentStudent()
        })

        val adapter = StudentAttendanceAdapter(studentList)
        CommonValue.presentStudentList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
            binding.studentCountTextview.text = "Count = ${it.size}"
            binding.progressBar.visibility = View.GONE
            visibilityStatus(!studentList.isNullOrEmpty())
        })
        binding.attendanceRecyclerview.adapter = adapter
        binding.attendanceRecyclerview.layoutManager = LinearLayoutManager(binding.root.context)
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNav.visibility = View.VISIBLE
    }

    private fun setUpToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_done_menu -> {
                    saveAttendance()
                    btObject.unRegisterReceiver()
                    val action = TakeAttendanceFragmentDirections.actionTakeAttendanceFragmentToSubjectAttendanceFragment()
                    findNavController().navigate(action)
                    true
                }

                R.id.option_help_menu -> {
                    //TODO show instruction
                    true
                }

                else -> false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            btObject.unRegisterReceiver()
            val action = TakeAttendanceFragmentDirections.actionTakeAttendanceFragmentToSubjectAttendanceFragment()
            findNavController().navigate(action)
        }
    }

    private fun saveAttendance() {
        val date =
            SimpleDateFormat("dd LLLL yyyy").format(Calendar.getInstance().time).toString().split(" ")
        val attendanceMap = hashMapOf<String, Boolean>()
        for (i in CommonValue.studentList.value!!) {
            attendanceMap[i.name] = i in studentList
        }

        val attendance = Attendance(date[0].toInt(), date[1], date[2].toInt(), attendanceMap)
        val attendanceList = CommonValue.attendanceList.value!!
        attendanceList.add(attendance)
        CommonValue.attendanceList.postValue(attendanceList)
        FireStoreDatabase().addNewAttendance(
            CommonValue.classList.value!![CommonValue.classPosition.value!!],
            attendance
        )
    }

    private fun visibilityStatus(status:Boolean) {
        if (status) {
            binding.attendanceRecyclerview.visibility = View.VISIBLE
            binding.emptyRecyclerview.visibility = View.GONE
        } else {
            binding.attendanceRecyclerview.visibility = View.GONE
            binding.emptyRecyclerview.visibility = View.VISIBLE
        }
    }
}