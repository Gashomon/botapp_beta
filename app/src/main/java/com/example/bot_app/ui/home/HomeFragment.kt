package com.example.bot_app.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bot_app.R
import com.example.bot_app.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val senderfield: EditText = binding.sender
        val receiverfield: TextView = binding.receiver
        val typefield: Spinner = binding.type
        val dest1field: Spinner = binding.dest1
        val dest2field: Spinner = binding.dest2
        val sendbtn: Button = binding.send

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.dests_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            dest1field.adapter = adapter
        }

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.dests_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            dest2field.adapter = adapter
        }

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.types_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            typefield.adapter = adapter
        }

        sendbtn.setOnClickListener(View.OnClickListener {
            // Code here executes on main thread after user presses button
            sendData(senderfield)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendData(pop: EditText){
//        val client = Socket("127.0.0.1", 9999)
//        client.outputStream.write("Hello from the client!".toByteArray())
//        client.close()
        pop.setText("hello")
        val bgt = BackGroundTask()
        bgt.viewModelScope
    }

    class BackGroundTask: ViewModel ()
    {
        val h = Handler()
        private var s: Socket? = null
        private var writer: PrintWriter? = null
        private var reader: DataInputStream? = null
        private var i: Int = 0
        private var send: String? = null
        private var get: String? = null
        init {
            viewModelScope.launch {

                doInBackground()
            }
        }
        private fun doInBackground(vararg voids: String?): Void? {
            Log.i("i", "hello");
            try {
                if (s == null) {
                    //change it to your IP
                    s = Socket("192.168.137.94", 6000)
                    writer = PrintWriter(s!!.getOutputStream())
                    reader = DataInputStream(
                        BufferedInputStream(s!!.getInputStream())
                    )
                    Log.i("i", "CONNECTED")
                }
                if ((i % 5) == 0) {
                    send = "me:fifth"
                    get = "me:fifth"
                } else {
                    send = "me:" + i.toString()
                    get = "me:" + i.toString()
                }
                if (i > 10) {
                    s!!.close()
                }
                writer?.write(send)
                writer?.flush()
                //                writer.close();
                i = i + 1
                h.post {
                    //                        Log.i("i", "lol");
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }

}