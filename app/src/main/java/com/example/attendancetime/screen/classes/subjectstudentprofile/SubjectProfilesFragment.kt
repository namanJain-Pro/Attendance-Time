package com.example.attendancetime.screen.classes.subjectstudentprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.databinding.FragmentSubjectProfilesBinding
import com.example.attendancetime.datamodel.dataclasses.Student
import androidx.recyclerview.widget.RecyclerView




class SubjectProfilesFragment : Fragment() {

    private lateinit var binding: FragmentSubjectProfilesBinding
    private lateinit var studentList: ArrayList<Student>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectProfilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentList = CommonValue.studentList.value!!

        val adapter = StudentProfileAdapter(studentList)
        CommonValue.studentList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })
        binding.studentProfileRecyclerView.adapter = adapter
        binding.studentProfileRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        binding.addStudent.setOnClickListener {
            val action = SubjectProfilesFragmentDirections.actionSubjectProfilesFragmentToAddStudentFragment()
            findNavController().navigate(action)
        }


    }
}