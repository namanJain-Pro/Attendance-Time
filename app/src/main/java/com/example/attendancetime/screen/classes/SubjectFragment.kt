package com.example.attendancetime.screen.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentSubjectBinding
import com.example.attendancetime.datamodel.firestoreDB.FireStoreDatabase

class SubjectFragment : Fragment() {

    private lateinit var binding: FragmentSubjectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        CommonValue.newStudentList.value = arrayListOf()
        FireStoreDatabase().fetchStudent(CommonValue.classPosition.value!!)
        FireStoreDatabase().fetchAttendance()
    }


}