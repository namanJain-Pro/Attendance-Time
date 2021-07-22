package com.example.attendancetime.screen.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.attendancetime.R
import com.example.attendancetime.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

/*
This fragment to show a Splash Screen
And Checking if anyone has signed in, if not then Sign-In Fragment will launch
else Dashboard will launch
 */

class SplashFragment :Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        auth = FirebaseAuth.getInstance()

        // Here we are showing splash screen for 1 sec
        // and checking for the user
        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser == null) {
                navController.navigate(R.id.action_splashFragment_to_signInFragment)
            } else {
                navController.navigate(R.id.action_splashFragment_to_dashboard)
            }
        }, 2500)
    }
}