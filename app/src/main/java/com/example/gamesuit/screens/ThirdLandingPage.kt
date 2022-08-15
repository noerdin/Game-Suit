package com.example.gamesuit.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gamesuit.R
import com.example.gamesuit.databinding.FragmentThirdLandingPageBinding

class ThirdLandingPage : Fragment() {

    var binding: FragmentThirdLandingPageBinding? = null
    fun getName(): String {
        return binding?.etName?.text.toString()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdLandingPageBinding.inflate(inflater, container,false)


        return binding?.root
    }

}