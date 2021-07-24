package com.example.attendancetime.datamodel.firestoreDB

import android.util.Log
import com.example.attendancetime.datamodel.dataclasses.Attendance
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.dataclasses.SubjectClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class FireStoreDatabase {

    companion object {
        private const val TAG = "FireStoreDatabase"
    }

    private val db = FirebaseFirestore.getInstance()
    private val DBName: String = FirebaseAuth.getInstance().currentUser?.displayName.plus("Record")

    fun addNewClass (subjectClass: SubjectClass) {
        db.collection(DBName)
            .document(subjectClass.subjectName.plus(subjectClass.section))
            .set(subjectClass)
            .addOnSuccessListener { Log.d(TAG, "addNewClass: Success in uploading the class") }
            .addOnFailureListener { Log.e(TAG, "addNewClass: Error writing class document, " + it.message) }
    }

    fun addNewAttendance(subjectClass: SubjectClass, attendance: Attendance) {
        db.collection(DBName)
            .document(subjectClass.subjectName.plus(subjectClass.section))
            .collection(subjectClass.subjectName.plus("Attendance"))
            .document("".plus(attendance.day).plus(attendance.month).plus(attendance.year))
            .set(attendance)
            .addOnSuccessListener { Log.d(TAG, "addNewAttendance: Success in uploading the attendance") }
            .addOnFailureListener { Log.e(TAG, "addNewAttendance: Error writing attendance document, " + it.message) }
    }

    fun getClasses(): ArrayList<SubjectClass> {
        var classList = arrayListOf<SubjectClass>()
        db.collection(DBName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    val subjectClassObject = document.toObject<SubjectClass>()
//                    Log.d(TAG, "getClasses: ${document.id} and ${document.data}")
                }
            }
            .addOnFailureListener { Log.e(TAG, "getClasses: Error in getting data, " + it.message) }
        return classList
    }
}