package com.example.attendancetime.datamodel.firestoreDB

import android.util.Log
import com.example.attendancetime.CommonValue
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.example.attendancetime.datamodel.dataclasses.SubjectClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*

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
            .document("".plus(attendance.day).plus(attendance.month).plus(attendance.year))
            .set(attendance)
            .addOnSuccessListener { Log.d(TAG, "addNewAttendance: Success in uploading the attendance") }
            .addOnFailureListener { Log.e(TAG, "addNewAttendance: Error writing attendance document, " + it.message) }
    }

    fun fetchClasses() {
        db.collection(dbName)
            .get()
            .addOnSuccessListener { documents ->
                val updatedClassList = CommonValue.classList.value ?: arrayListOf()
                var duplicateCheck = true

                for (document in documents) {
                    val subjectClassObject = document.toObject<SubjectClass>()
                    if (!updatedClassList.contains(subjectClassObject)) {
                        duplicateCheck = false
                        updatedClassList.add(subjectClassObject)
                    }
                    Log.d(TAG, "fetchClasses: Data added to classList, state: $updatedClassList")
                }

                if (!duplicateCheck) {
                    // Now set LiveData object
                    CommonValue.classList.postValue(updatedClassList)
                    // Set class list state to fetched
                    CommonValue.classListFetched.postValue(true)
                }
            }
            .addOnFailureListener { Log.e(TAG, "getClasses: Error in getting data, " + it.message) }
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