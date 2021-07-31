package com.example.attendancetime.datamodel.dataclasses

import com.example.attendancetime.R

data class SubjectClass(
    val imageId: Int,
    val subjectName: String,
    val section: String,
    val students: ArrayList<Student>) {

    constructor() : this(R.drawable.classbackground1, "", "", arrayListOf())

}