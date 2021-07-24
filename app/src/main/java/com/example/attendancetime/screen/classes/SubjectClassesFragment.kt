package com.example.attendancetime.screen.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancetime.databinding.FragmentSubjectClassesBinding

class SubjectClassesFragment : Fragment() {

    private lateinit var binding: FragmentSubjectClassesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectClassesBinding.inflate(inflater, container, false)
        return binding.root
    }

}