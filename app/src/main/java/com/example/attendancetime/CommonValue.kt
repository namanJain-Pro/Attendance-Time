package com.example.attendancetime

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.dataclasses.SubjectClass

object CommonValue {

    val btDeviceList = MutableLiveData<ArrayList<BluetoothDevice>>()
    val studentList = MutableLiveData<ArrayList<Student>>()
    val classList = MutableLiveData<ArrayList<SubjectClass>>()
    val classPosition = MutableLiveData<Int>()

    init {
        btDeviceList.value = arrayListOf()
        studentList.value = arrayListOf()
        classList.value = arrayListOf()
        classPosition.value = -1
    }
}