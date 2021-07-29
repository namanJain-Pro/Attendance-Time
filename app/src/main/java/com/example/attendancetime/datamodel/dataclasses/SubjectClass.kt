package com.example.attendancetime.datamodel.dataclasses

data class SubjectClass(
    val subjectName: String,
    val section: String,
    val students: ArrayList<Student>) {

    constructor() : this("", "", arrayListOf())

}