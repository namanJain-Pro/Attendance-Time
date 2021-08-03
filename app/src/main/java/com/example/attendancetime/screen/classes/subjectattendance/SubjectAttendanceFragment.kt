package com.example.attendancetime.screen.classes.subjectattendance

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentSubjectAttendanceBinding
import com.example.attendancetime.datamodel.dataclasses.Attendance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SubjectAttendanceFragment :
    Fragment(),
    DatePickerDialog.OnDateSetListener,
    SubjectAttendanceAdapter.OnItemClickListener
{
    
    companion object {
        private const val TAG = "SubjectAttendanceFragme"
    }
    
    private lateinit var binding: FragmentSubjectAttendanceBinding
    private lateinit var attendanceRecord: ArrayList<Attendance>
    private lateinit var adapter: SubjectAttendanceAdapter
    private var filterToggle = false
    private var listSort = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        attendanceRecord = CommonValue.attendanceList.value!!

        binding.takeAttendanceBtn.setOnClickListener {
            if (isAttendanceAlreadyTaken()) {
                Toast.makeText(
                    binding.root.context,
                    "Attendance for today is already been taken",
                    Toast.LENGTH_LONG)
                    .show()
            } else {
                val action =
                    SubjectAttendanceFragmentDirections.actionSubjectAttendanceFragmentToTakeAttendanceFragment()
                findNavController().navigate(action)
            }
        }

        adapter = SubjectAttendanceAdapter(attendanceRecord, this)
        CommonValue.attendanceList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
            visibilityStatus(!attendanceRecord.isNullOrEmpty())
        })
        binding.attendanceRecordRecyclerView.adapter = adapter
        binding.attendanceRecordRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.date_sort -> {
                    if (filterToggle) {
                        filterToggle = false
                        adapter.updateData(attendanceRecord)
                    } else {
                        filterToggle = true
                        showDatePickerDialog()
                    }
                    true
                }

                R.id.asec_desec_sort -> {
                    if (listSort) {
                        listSort = false
                        adapter.updateData(attendanceRecord)
                    } else {
                        listSort = true
                        val list = CommonValue.attendanceList.value!!.reversed()
                        val reversedAttendanceList = arrayListOf<Attendance>()
                        for (item in list) {
                            reversedAttendanceList.add(item)
                        }
                        adapter.updateData(reversedAttendanceList)
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun visibilityStatus(status:Boolean) {
        if (status) {
            binding.attendanceRecordRecyclerView.visibility = View.VISIBLE
            binding.emptyRecyclerview.visibility = View.GONE
        } else {
            binding.attendanceRecordRecyclerView.visibility = View.GONE
            binding.emptyRecyclerview.visibility = View.VISIBLE
        }
    }

    private fun isAttendanceAlreadyTaken(): Boolean {
        val date =
            SimpleDateFormat("dd LLLL yyyy").format(Calendar.getInstance().time).toString().split(" ")
        val todayDate = "${date[0].toInt()} ${date[1]} ${date[2].toInt()}"

        for (attendance in attendanceRecord) {
            if (attendance.getDate() == todayDate) {
                return true
            }
        }

        return false
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            binding.root.context,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val monthInAlphabet = when(month) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> ""
        }

        val sortedAttendanceList = arrayListOf<Attendance>()

        for (attendance in attendanceRecord) {
            if (attendance.year == year && attendance.month == monthInAlphabet && attendance.day == dayOfMonth) {
                Log.d(TAG, "onDateSet: Attendance for date found")
                sortedAttendanceList.add(attendance)
                break
            }
        }
        adapter.updateData(sortedAttendanceList)
    }

    override fun onItemClick(position: Int) {
        CommonValue.attendanceItemPosition.value = position
        val action = SubjectAttendanceFragmentDirections.actionSubjectAttendanceFragmentToAttendanceItemFragment()
        findNavController().navigate(action)
    }
}