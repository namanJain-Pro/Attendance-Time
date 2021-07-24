package com.example.attendancetime.datamodel.dataclasses

data class Student(val name: String, val section: String, val rollNumber: Long, val macAddress: String) {

    // 0 -> Invalid Check or two objects are not same
    // 1 -> objects are same
    // 2 -> objects are not same but both same same mac address
    fun toEqual(other: Any?) : Int{

        if (other is Student) {
            if (this.name == other.name && this.rollNumber == other.rollNumber) {
                return 1
            } else if (this.macAddress == other.macAddress) {
                return 2
            }
        }

        return 0
    }
}
