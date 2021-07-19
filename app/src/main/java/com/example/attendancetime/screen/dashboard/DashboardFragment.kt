package com.example.attendancetime.screen.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentDashboardBinding
import com.example.attendancetime.datamodel.SubjectClass
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

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

        binding.toolbar.inflateMenu(R.menu.dashboard_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.add_new_class -> {
                    findNavController().navigate(R.id.action_dashboardFragment_to_addNewClassFragment)
                    true
                }
                R.id.sign_out -> {
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_dashboardFragment_to_signInFragment)
                    true
                }

                else -> false
            }
        }

        val tempList = arrayListOf<SubjectClass>()

        if (tempList.isNotEmpty()) {
            val adapter =  DashboardRecyclerAdapter(tempList)
            binding.recyclerViewClasses.adapter = adapter
            binding.recyclerViewClasses.layoutManager = LinearLayoutManager(binding.root.context)
        } else {
            binding.recyclerViewClasses.visibility = View.GONE
            binding.emptyWarning.visibility = View.VISIBLE
        }

    }
}