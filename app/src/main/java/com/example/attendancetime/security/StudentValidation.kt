package com.example.attendancetime.security

import android.util.Log
import com.example.attendancetime.CommonValue
import com.example.attendancetime.datamodel.Student

/*
This class will observe the security issues and identify user
 */

class StudentValidation {
    companion object {
        private const val TAG = "StudentValidation"
    }

    private var studentList: ArrayList<Student> = CommonValue.studentList.value!!

    // This method will check if student already exist in list or not
    // And also if someone has changed there name
    fun studentAlreadyExist(student: Student): Boolean {
        Log.d(TAG, "studentAlreadyExist: Validating if student " + student.name + "already exist or not")
        if (!studentList.isNullOrEmpty()) {
            for (obj in studentList) {
                if (obj.toEqual(student) == 1) {
                    return true
                } else if (obj.toEqual(student) == 2) {
                    //TODO launch a dialog that a student has changed his name and try to fake attendance
                }
            }
        }
        Log.d(TAG, "studentAlreadyExist: Student doesn't exist in list")
        return false
    }
}