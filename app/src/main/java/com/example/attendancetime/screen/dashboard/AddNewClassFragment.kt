package com.example.attendancetime.screen.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.bluetoothlogic.DiscoverDevices
import com.example.attendancetime.bluetoothlogic.IdentifyDevices
import com.example.attendancetime.databinding.FragmentAddNewClassBinding
import com.example.attendancetime.datamodel.Student
import com.example.attendancetime.datamodel.SubjectClass

/*
Here teacher can add new class
Should provide some details for the class
 */

class AddNewClassFragment : Fragment() {

    private lateinit var binding: FragmentAddNewClassBinding
    private lateinit var btObject: DiscoverDevices
    private lateinit var identifyDevices: IdentifyDevices
    private lateinit var studentList: ArrayList<Student>
    private lateinit var classList: ArrayList<SubjectClass>

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
        // Initializing the list
        studentList = CommonValue.studentList.value!!
        classList = CommonValue.classList.value!!

        btObject = DiscoverDevices(binding.root.context)
        identifyDevices = IdentifyDevices()

        // When AddStudent button is clicked
        // Starting the bluetooth and Discovering the devices
        // When device are found, the bluetooth device list will update
        binding.addNewStudent.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            btObject.discoverDevices()
        }

        // Here we are looking for Bluetooth device list updates
        // When the list is updated, than we need to separate student devices form other devices
        CommonValue.btDeviceList.observe(viewLifecycleOwner, {
            identifyDevices.validateDevices()
        })

        // Setting up the recycler view
        val adapter = StudentRecyclerAdapter(studentList)
        // When we find some student, student list got updated
        // Than we will update the content of recycler view and change the visibility status
        CommonValue.studentList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
            visibilityStatus(!studentList.isNullOrEmpty())
        })
        binding.studentRecyclerView.adapter = adapter
        binding.studentRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
    }

    // Inflating the toolbar and listening for the clicks
    private fun setUpToolbar() {
        binding.toolbar.inflateMenu(R.menu.new_class_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_done_menu -> {
                    // If any field is left empty than we will show the message for filling them
                    // Else we will create the new class if all parameter are filled
                    if (notEmptyFieldsCheck()) {
                        classList.add(SubjectClass(binding.editTextSubjectName.text.toString(),
                            binding.editTextSection.text.toString(),
                            studentList))
                        CommonValue.classList.postValue(classList)
                        btObject.unRegisterReceiver()
                        findNavController().navigate(R.id.action_addNewClassFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(binding.root.context, "Please fill all the field", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                else -> false
            }
        }
        // Here we are adding the back button
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addNewClassFragment_to_dashboardFragment)
        }
    }

    // Helper Functions

    private fun notEmptyFieldsCheck() : Boolean {
        return binding.editTextSubjectName.text.trim().isNotEmpty()
                && binding.editTextSection.text.trim().isNotEmpty()
                && CommonValue.studentList.value?.isNotEmpty() == true
    }

    private fun visibilityStatus(status:Boolean) {
        if (status) {
            binding.studentRecyclerView.visibility = View.VISIBLE
            binding.emptyRecyclerview.visibility = View.GONE
        } else {
            binding.studentRecyclerView.visibility = View.GONE
            binding.emptyRecyclerview.visibility = View.VISIBLE
        }
    }
}