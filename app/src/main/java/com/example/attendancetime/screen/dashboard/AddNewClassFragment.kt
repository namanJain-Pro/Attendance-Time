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
import com.example.attendancetime.datamodel.dataclasses.Student
import com.example.attendancetime.datamodel.dataclasses.SubjectClass
import com.example.attendancetime.datamodel.firestoreDB.FireStoreDatabase
import kotlin.random.Random

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
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_done_menu -> {
                    // If any field is left empty than we will show the message for filling them
                    // Else we will create the new class if all parameter are filled
                    if (notEmptyFieldsCheck()) {
                        val subjectClass = SubjectClass(
                            getBackground(),
                            binding.editTextSubjectName.editText?.text.toString(),
                            binding.editTextSection.editText?.text.toString(),
                            studentList)
                        classList.add(subjectClass)

                        CommonValue.classList.postValue(classList)
                        FireStoreDatabase().addNewClass(subjectClass)

                        btObject.unRegisterReceiver()
                        val action = AddNewClassFragmentDirections.actionAddNewClassFragmentToDashboardFragment()
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(binding.root.context, "Please fill all the field", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                else -> false
            }
        }
        // Here we are adding the back button
        binding.toolbar.setNavigationOnClickListener {
            btObject.unRegisterReceiver()
            findNavController().navigate(R.id.action_addNewClassFragment_to_dashboardFragment)
        }
    }

    // Helper Functions
    private fun notEmptyFieldsCheck() : Boolean {
        return binding.editTextSubjectName.editText?.text.toString().trim().isNotEmpty()
                && binding.editTextSection.editText?.text.toString().trim().isNotEmpty()
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

    private fun getBackground(): Int {
        return when(Random.nextInt(1, 10)) {
            1 -> R.drawable.classbackground1
            2 -> R.drawable.classbackground2
            3 -> R.drawable.classbackground3
            4 -> R.drawable.classbackground4
            5 -> R.drawable.classbackground5
            6 -> R.drawable.classbackground6
            7 -> R.drawable.classbackground7
            8 -> R.drawable.classbackground8
            9 -> R.drawable.classbackground9

            else -> R.drawable.classbackground1
        }
    }
}