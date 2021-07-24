package com.example.attendancetime.screen.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancetime.databinding.FragmentSubjectProfilesBinding

class SubjectProfilesFragment : Fragment() {

    private lateinit var binding: FragmentSubjectProfilesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectProfilesBinding.inflate(inflater, container, false)
        return binding.root
    }

}