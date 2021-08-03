package com.example.attendancetime.datamodel.dataclasses

data class Attendance(
    val day: Int,
    val month: String,
    val year: Int,
    val attendance: HashMap<String, Boolean>) {

    constructor() : this(0, "", 0, hashMapOf<String, Boolean>())

    fun getDate() : String {
        return "$day $month $year"
    }
}