package com.example.attendancetime.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.attendancetime.datamodel.Student

class AttendanceViewModal : ViewModel() {

    val students = MutableLiveData<List<Student>>()

    init {
        students.value = arrayListOf()
    }


}