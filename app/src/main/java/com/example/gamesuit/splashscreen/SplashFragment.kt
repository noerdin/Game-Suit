package com.example.gamesuit.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gamesuit.R
import com.example.gamesuit.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    var binding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding?.apply {
            Glide.with(this@SplashFragment)
                .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png").into(ivSplashScreen1)
        }

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }, 3000)

        return binding?.root
    }

}