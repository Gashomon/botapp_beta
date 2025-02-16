package com.example.bot_app.ui.response

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bot_app.R
import com.example.bot_app.databinding.FragmentResponseBinding

class ResponseFragment : Fragment() {


    private var _binding: FragmentResponseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentResponseBinding.inflate(inflater, container, false)
        var root : View = binding.root
        var backbtn : Button = binding.button

        binding.textView3.setText(arguments?.getString("MainText") ?: "hello")
        binding.textView4.setText(arguments?.getString("SubText") ?: "hi")


        backbtn.setOnClickListener(View.OnClickListener {
            // Code here executes on main thread after user presses button
            findNavController().navigate(R.id.action_response_fragment_to_first_fragment)
        })

        return root
    }
}