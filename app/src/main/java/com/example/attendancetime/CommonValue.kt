package com.example.attendancetime

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.dataclasses.SubjectClass

object CommonValue {

    val btDeviceList = MutableLiveData<ArrayList<BluetoothDevice>>()
    val attendanceList = MutableLiveData<ArrayList<Attendance>>()
    val presentStudentList = MutableLiveData<ArrayList<Student>>()
    val studentList = MutableLiveData<ArrayList<Student>>()
    val newStudentList = MutableLiveData<ArrayList<Student>>()
    val classList = MutableLiveData<ArrayList<SubjectClass>>()
    val classListFetched = MutableLiveData<Boolean>()
    val classPosition = MutableLiveData<Int>()
    val attendanceItemPosition = MutableLiveData<Int>()

    init {
        btDeviceList.value = arrayListOf()
        attendanceList.value = arrayListOf()
        presentStudentList.value = arrayListOf()
        studentList.value = arrayListOf()
        newStudentList.value = arrayListOf()
        classList.value = arrayListOf()
        classListFetched.value = false
        classPosition.value = -1
        attendanceItemPosition.value = -1
    }

    fun destroyState() {
        btDeviceList.postValue(arrayListOf())
        attendanceList.postValue(arrayListOf())
        presentStudentList.postValue(arrayListOf())
        studentList.postValue(arrayListOf())
        newStudentList.postValue(arrayListOf())
        classList.postValue(arrayListOf())
        classListFetched.postValue(false)
        classPosition.postValue(-1)
        attendanceItemPosition.postValue(-1)
     }
}