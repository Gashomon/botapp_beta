package com.example.bot_app.ui.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.bot_app.R
import com.example.bot_app.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        var root : View = binding.root
        var openbtn : Button = binding.button3

        openbtn.setOnClickListener(View.OnClickListener {
            // Code here executes on main thread after user presses button
            findNavController().navigate(R.id.navigation_home)
        })

        return root
    }
}