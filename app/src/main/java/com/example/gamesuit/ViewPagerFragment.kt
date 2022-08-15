package com.example.gamesuit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.gamesuit.screens.FirstLandingPage
import com.example.gamesuit.screens.SecondLandingPage
import com.example.gamesuit.screens.ThirdLandingPage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstLandingPage(),
            SecondLandingPage(),
            ThirdLandingPage()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle

        )

        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager2.adapter = adapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tlIndicator)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        }.attach()

        val ivnext = view.findViewById<ImageView>(R.id.ivNext)
        ivnext.setOnClickListener {
            Log.d("masuk","viewPager2.currentItem befor: ${viewPager2.currentItem}")
            Log.d("masuk","adapter.itemCount: ${adapter.itemCount}")
            Log.d("masuk","fragment befor: ${adapter.fragmentList.getOrNull(viewPager2.currentItem)}")
            if (viewPager2.currentItem + 1 < adapter.itemCount) {
                viewPager2.currentItem += 1
                Log.d("masuk","viewPager2.currentItem after: ${viewPager2.currentItem}")
                Log.d("masuk","fragment after: ${adapter.fragmentList.getOrNull(viewPager2.currentItem)}")
            } else {
                val fragment = adapter.fragmentList.getOrNull(viewPager2.currentItem)
                val name = (fragment as? ThirdLandingPage)?.getName().orEmpty()
                if (name.isBlank()) {
                    Toast.makeText(
                        requireContext(), "Belum Mengisi Nama", Toast.LENGTH_LONG
                    ).show()
                } else {
                    val bundle = bundleOf(HomeFragment.KEY_NAME to name)
                    findNavController().navigate(
                        R.id.action_viewPagerFragment_to_homeFragment, bundle
                    )
                }
            }
        }

        return view
    }


}