package com.example.attendancetime.screen.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment :Fragment() {
    
    companion object {
        private const val TAG = "SplashFragment"
    }

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser == null) {
                Log.d(TAG, "onCreate: User has not signed in")
                navController.navigate(R.id.action_splashFragment_to_signInFragment)
            } else {
                Log.d(TAG, "onCreate: Going to  Dashboard")
                navController.navigate(R.id.action_splashFragment_to_dashboard)
            }
        }, 1000)
    }
}