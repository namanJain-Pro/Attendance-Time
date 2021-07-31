package com.example.attendancetime.screen.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancetime.databinding.FragmentSubjectAttendanceBinding

class SubjectAttendanceFragment : Fragment() {

    private lateinit var binding: FragmentSubjectAttendanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

}