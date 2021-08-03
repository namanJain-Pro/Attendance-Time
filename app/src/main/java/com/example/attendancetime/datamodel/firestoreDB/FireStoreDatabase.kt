package com.example.attendancetime.datamodel.firestoreDB

import android.util.Log
import com.example.attendancetime.CommonValue
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.dataclasses.SubjectClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*
import kotlin.collections.ArrayList

class FireStoreDatabase {

    companion object {
        private const val TAG = "FireStoreDatabase"
    }

    private val db = FirebaseFirestore.getInstance()
    private val dbName: String = getDbName(FirebaseAuth.getInstance().currentUser?.displayName ?: "")

    fun addNewClass (subjectClass: SubjectClass) {
        db.collection(dbName)
            .document(subjectClass.subjectName.plus(subjectClass.section))
            .set(subjectClass)
            .addOnSuccessListener { Log.d(TAG, "addNewClass: Success in uploading the class") }
            .addOnFailureListener { Log.e(TAG, "addNewClass: Error writing class document, " + it.message) }
    }

    fun addNewAttendance(subjectClass: SubjectClass, attendance: Attendance) {
        db.collection(dbName)
            .document(subjectClass.subjectName.plus(subjectClass.section))
            .collection(subjectClass.subjectName.plus("Attendance"))
            .document("${attendance.day}_${attendance.month}_${attendance.year}")
            .set(attendance)
            .addOnSuccessListener { Log.d(TAG, "addNewAttendance: Success in uploading the attendance") }
            .addOnFailureListener { Log.e(TAG, "addNewAttendance: Error writing attendance document, " + it.message) }
    }

    fun addNewStudent(subjectClass: SubjectClass, studentList: ArrayList<Student>) {
        Log.d(TAG, "addNewStudent: $studentList")
        if (!studentList.isNullOrEmpty()) {
            db.collection(dbName)
                .document(subjectClass.subjectName.plus(subjectClass.section))
                .update("students", studentList)
                .addOnSuccessListener {
                    Log.d(TAG, "addNewStudent: New Student Added")
                    updateAttendanceForNewStudent(subjectClass, studentList)
                }
                .addOnFailureListener { Log.d(TAG, "addNewStudent: Error in updating student list") }
        } else {
            Log.e(TAG, "addNewStudent: Error student list was empty or null")
        }
    }

    fun fetchAttendance(subjectClass: SubjectClass) {
        db.collection(dbName)
            .document(subjectClass.subjectName.plus(subjectClass.section))
            .collection(subjectClass.subjectName.plus("Attendance"))
            .get()
            .addOnSuccessListener { documents ->
                val attendanceList = CommonValue.attendanceList.value!!

                for (document in documents) {
                    val attendanceClassObject = document.toObject<Attendance>()
                    if (!attendanceList.contains(attendanceClassObject)) {
                        attendanceList.add(attendanceClassObject)
                    }
                }

                CommonValue.attendanceList.postValue(attendanceList)
            }
            .addOnFailureListener {
                Log.e(TAG, "fetchAttendance: Error in fetching attendance, " + it.message)
            }

    }

    fun fetchStudent(classPosition: Int) {
        if (classPosition != -1 && classPosition < CommonValue.classList.value?.size!!) {
            CommonValue.studentList.postValue(CommonValue.classList.value?.get(classPosition)?.students)
        }
    }

    fun fetchClasses() {
        db.collection(dbName)
            .get()
            .addOnSuccessListener { documents ->
                val updatedClassList = CommonValue.classList.value ?: arrayListOf()

                for (document in documents) {
                    val subjectClassObject = document.toObject<SubjectClass>()
                    if (!updatedClassList.contains(subjectClassObject)) {
                        updatedClassList.add(subjectClassObject)
                    }
                    Log.d(TAG, "fetchClasses: Data added to classList, state: $updatedClassList")
                }

                // Now set LiveData object
                CommonValue.classList.postValue(updatedClassList)
                // Set class list state to fetched
                CommonValue.classListFetched.postValue(true)
            }
            .addOnFailureListener { Log.e(TAG, "getClasses: Error in getting data, " + it.message) }
    }

    private fun updateAttendanceForNewStudent(subjectClass: SubjectClass, studentList: ArrayList<Student>) {
        val attendanceList = CommonValue.attendanceList.value!!

        for (attendance in attendanceList) {
            var isChange = false
            for (student in studentList) {
                if (!attendance.attendance.containsKey(student.name)) {
                    isChange = true
                    attendance.attendance[student.name] = false
                }
            }
            if (isChange) {
                addNewAttendance(subjectClass, attendance)
            }
        }
    }

    private fun getDbName(userName: String): String {
        if(userName.isNotEmpty()) {
            var str = ""
            val arr = userName.lowercase().split(" ")
            for (i in arr) {
                str += i
                str += "_"
            }
            str += "record"
            return str
        }

        return "un-named".plus("_".plus(Random().nextInt() * Random().nextInt())).plus("_record")
    }
}