package com.example.attendancetime

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import com.example.attendancetime.datamodel.Student
import com.example.attendancetime.datamodel.SubjectClass

object CommonValue {

    val btDeviceList = MutableLiveData<ArrayList<BluetoothDevice>>()
    val studentList = MutableLiveData<ArrayList<Student>>()
    val classList = MutableLiveData<ArrayList<SubjectClass>>()

    init {
        btDeviceList.value = arrayListOf()
        studentList.value = arrayListOf()
        classList.value = arrayListOf()
    }
}