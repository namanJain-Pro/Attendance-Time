package com.example.attendancetime.screen.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.bluetoothlogic.DiscoverDevices
import com.example.attendancetime.bluetoothlogic.IdentifyDevices
import com.example.attendancetime.databinding.FragmentAddStudentBinding
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.firestoreDB.FireStoreDatabase
import com.example.attendancetime.screen.dashboard.StudentRecyclerAdapter

class AddStudentFragment : Fragment() {
    private lateinit var binding: FragmentAddStudentBinding
    private lateinit var btObject: DiscoverDevices
    private lateinit var identifyDevices: IdentifyDevices
    private lateinit var newStudentList: ArrayList<Student>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        newStudentList = CommonValue.newStudentList.value!!
        btObject = DiscoverDevices(binding.root.context)
        identifyDevices = IdentifyDevices()

        binding.addStudent.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            btObject.discoverDevices()
        }

        CommonValue.btDeviceList.observe(viewLifecycleOwner, {
            identifyDevices.validateNewDevices()
        })

        val adapter = StudentRecyclerAdapter(newStudentList)
        CommonValue.newStudentList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
            visibilityStatus(!newStudentList.isNullOrEmpty())
        })
        binding.studentRecyclerview.adapter = adapter
        binding.studentRecyclerview.layoutManager = LinearLayoutManager(binding.root.context)
    }

    private fun setUpToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_done_menu -> {
                    addNewStudentInDb()
                    btObject.unRegisterReceiver()
                    val action =
                        AddStudentFragmentDirections.actionAddStudentFragmentToSubjectProfilesFragment()
                    findNavController().navigate(action)
                    true
                }

                R.id.option_help_menu -> {
                    //TODO show instruction

                    true
                }

                else -> false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            btObject.unRegisterReceiver()
            val action = AddStudentFragmentDirections.actionAddStudentFragmentToSubjectProfilesFragment()
            findNavController().navigate(action)
        }
    }

    private fun addNewStudentInDb() {
        FireStoreDatabase().addNewStudent(CommonValue.studentList.value!!,
            CommonValue.classList.value!![CommonValue.classPosition.value!!]
        )
    }


    private fun visibilityStatus(status:Boolean) {
        if (status) {
            binding.studentRecyclerview.visibility = View.VISIBLE
            binding.emptyRecyclerview.visibility = View.GONE
        } else {
            binding.studentRecyclerview.visibility = View.GONE
            binding.emptyRecyclerview.visibility = View.VISIBLE
        }
    }
}