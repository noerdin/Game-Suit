package com.example.gamesuit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.gamesuit.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    companion object {
        const val KEY_NAME = "name"
    }

    var binding: FragmentHomeBinding? = null
    override fun onResume() {
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(KEY_NAME).orEmpty()
        setTextName(name)
        binding?.clMenu?.let {
            val snackbar = Snackbar.make(
                requireContext(),
                it, "Selamat Datang ${name}", Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("Tutup") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
        binding?.ivVsCom?.setOnClickListener {
            val bundle = bundleOf(
                GameFragment.KEY_VS_PLAYER to false,
                GameFragment.KEY_NAME to name
            )
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment, bundle)
        }
        binding?.ivVsPlayer?.setOnClickListener {
            val bundle = bundleOf(
                GameFragment.KEY_VS_PLAYER to true,
                GameFragment.KEY_NAME to name
            )
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun setTextName(name: String) {
        binding?.tvVsPemain?.isVisible = true
        binding?.tvVsCom?.isVisible = true
        binding?.tvVsPemain?.text = getString(R.string.text_vs_pemain, name)
        binding?.tvVsCom?.text = getString(R.string.text_vs_cpu, name)
    }

}