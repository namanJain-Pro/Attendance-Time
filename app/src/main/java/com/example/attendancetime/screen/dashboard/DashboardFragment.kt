package com.example.attendancetime.screen.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.CommonValue
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentDashboardBinding
import com.example.attendancetime.datamodel.dataclasses.SubjectClass
import com.google.firebase.auth.FirebaseAuth

/*
This is the Dashboard fragment contain the Subject classes
I am using recycler view for displaying the list of classes that teacher can add
 */

class DashboardFragment : Fragment(), DashboardRecyclerAdapter.OnClassItemClickListener {
    // Binding is awesome implementation and replace findviewbyId
    // Developer can directly access view
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var classList: ArrayList<SubjectClass>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        classList = CommonValue.classList.value!!

        val adapter =  DashboardRecyclerAdapter(classList, this)
        CommonValue.classList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
            visibilityStatus(!classList.isNullOrEmpty())
        })
        visibilityStatus(!classList.isNullOrEmpty())
        binding.recyclerViewClasses.adapter = adapter
        binding.recyclerViewClasses.layoutManager = LinearLayoutManager(binding.root.context)

    }

    //This function is creating the toolbar and menu item for it
    //And Listening for the clicks
    private fun setUpToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.add_new_class -> {
                    // Launch AddNewClass Fragment
                    val action = DashboardFragmentDirections.actionDashboardFragmentToAddNewClassFragment()
                    findNavController().navigate(action)
                    true
                }
                R.id.sign_out -> {
                    // Signing out the user and sending it to sign in fragment
                    FirebaseAuth.getInstance().signOut()
                    val action = DashboardFragmentDirections.actionDashboardFragmentToSignInFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }
    }

    // If there no class present a message will be shown
    // If class is added than message will be replaced with the list of class
    private fun visibilityStatus(status:Boolean) {
        if (status) {
            binding.recyclerViewClasses.visibility = View.VISIBLE
            binding.emptyWarning.visibility = View.GONE
        } else {
            binding.recyclerViewClasses.visibility = View.GONE
            binding.emptyWarning.visibility = View.VISIBLE
        }
    }

    override fun onClassItemClick(position: Int) {
        CommonValue.classPosition.value = position
        val action = DashboardFragmentDirections.actionDashboardFragmentToSubjectFragment()
        findNavController().navigate(action)
    }
}