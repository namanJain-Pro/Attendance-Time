package com.example.attendancetime.screen.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentAddNewClassBinding
import com.example.attendancetime.screen.AttendanceViewModal

class AddNewClassFragment : Fragment() {
    private lateinit var binding: FragmentAddNewClassBinding
    private val myViewModal: AttendanceViewModal by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        binding.addNewStudent.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            //TODO fetch bluetooth devices
        }

    }

    private fun setUpToolbar() {
        binding.toolbar.inflateMenu(R.menu.new_class_menu)
        binding.toolbar.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.option_done_menu -> {
                    if (notEmptyFieldsCheck()) {
                        //TODO Make Create new subject class
                        findNavController().navigate(R.id.action_addNewClassFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(binding.root.context, "Please fill all the field", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addNewClassFragment_to_dashboardFragment)
        }
    }

    private fun notEmptyFieldsCheck() : Boolean {
        return binding.editTextSubjectName.text.trim().isNotEmpty()
                && binding.editTextSection.text.trim().isNotEmpty()
                && myViewModal.students.value?.isNotEmpty() == true
    }
}