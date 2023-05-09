package com.rmsxo.cream.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rmsxo.cream.R
import com.rmsxo.cream.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animal: Array<Int> = arrayOf(
            R.drawable.animal1,
            R.drawable.animal2,
            R.drawable.animal3,
            R.drawable.animal4,
            R.drawable.animal5
        )

        val adapter = HomeRecyclerViewAdapter(animal)
        binding.RecyclerViewId.adapter = adapter
        binding.RecyclerViewId.layoutManager = LinearLayoutManager(requireContext())

        // BottomNavigationView 객체를 찾지 못해서 스코프를 사용
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            var down = false
            var height: Float? = null
            val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)

            binding.RecyclerViewId.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (height == null) {
                        height = bottomNavView.height.toFloat()
                    }
                    if (dy > 0) {
                        if (down) {
                            down = false
                            bottomNavView.clearAnimation()
                            bottomNavView.animate().translationY(height!!).duration = 200
                        }
                    } else {
                        if (!down) {
                            down = true
                            bottomNavView.clearAnimation()
                            bottomNavView.animate().translationY(0f).duration = 200
                        }
                    }
                }
            })
        }
    }
}