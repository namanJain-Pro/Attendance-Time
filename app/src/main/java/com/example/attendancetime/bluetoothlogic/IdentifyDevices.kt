package com.example.attendancetime.bluetoothlogic

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.example.attendancetime.CommonValue
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.security.StudentValidation

/*
In this class we will separate device with the student devices
I have decided to use a format for name of student
Format - "Roll_Section_Name"
 */

class IdentifyDevices {
    companion object {
        private const val TAG = "IdentifyDevices"
    }

    private lateinit var btDevices: ArrayList<BluetoothDevice>
    private lateinit var studentList: ArrayList<Student>
    private lateinit var newStudentList: ArrayList<Student>

    // Here we are separating device on the basis of name
    // and updating student list
    fun validateDevices() {
        btDevices = CommonValue.btDeviceList.value!!
        studentList = CommonValue.studentList.value!!

        for (device in btDevices) {
            if (correctNameFormat(device.name ?: "")) {
                val details = device.name.split("_")
                val student = Student(details[2], details[1], details[0].toLong(), device.address)
                if (!StudentValidation().studentAlreadyExist(student)) {
                    Log.d(TAG, "validateDevices: Updated the student list")
                    studentList.add(student)
                }
            }
        }
        CommonValue.studentList.postValue(studentList)
    }

    fun validateNewDevices() {
        btDevices = CommonValue.btDeviceList.value!!
        newStudentList = CommonValue.newStudentList.value!!

        for (device in btDevices) {
            if (correctNameFormat(device.name ?: "")) {
                val details = device.name.split("_")
                val student = Student(details[2], details[1], details[0].toLong(), device.address)
                if (!StudentValidation().studentAlreadyExist(student)) {
                    Log.d(TAG, "validateDevices: Updated the student list")
                    newStudentList.add(student)
                } else {
                    Log.d(TAG, "validateNewDevices: student already exists")
                }
            }
        }
        CommonValue.newStudentList.postValue(newStudentList)
    }

    private fun correctNameFormat(name: String): Boolean {
        val details = name.split("_")

        if (details.size == 3) {
            if (details[0].toIntOrNull() != null) {
                return true
            }
        }
        return false
    }
}