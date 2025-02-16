package com.example.bot_app.ui.home

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bot_app.ClientConn
import com.example.bot_app.R
import com.example.bot_app.databinding.FragmentHomeBinding
import com.example.bot_app.databinding.FragmentResponseBinding
import java.net.InetAddress
import java.net.Socket
import java.security.KeyStore.TrustedCertificateEntry


class HomeFragment : Fragment() {
    var hostIP: InetAddress = InetAddress.getByName("192.168.137.231")
//    var connection: Socket = Socket(hostIP, 9999)
//    val con: ClientConn = ClientConn(hostIP)

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        socketConnect()
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val senderfield: EditText = binding.sender
        val receiverfield: EditText = binding.receiver
        val typefield: Spinner = binding.type
        val dest1field: Spinner = binding.dest1
        val dest2field: Spinner = binding.dest2
        val txv: TextView = binding.typetxt
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
            val bund: Bundle = Bundle()
            val mainText: String
            val smolText: String

            var success = false
            val password = makepass()
            try {
//                sendData(senderfield, receiverfield, typefield, dest1field, dest2field, password, txv, con)
                success = true
            }
            catch (e:Exception) {
                success = false
            }

            if(success){
                mainText = "SUCCESSFUL REQUEST.\n Robot OTW"
                smolText = "Password : $password"
            }
            else{
                mainText = "UNSUCCESSFUL REQUEST"
                smolText = "Try Again Later"
            }
            bund.putString("MainText", mainText)
            bund.putString("SubText", smolText)
            findNavController().navigate(R.id.action_navigation_home_to_response_fragment, bund)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
//        con.run()

    }
    private fun sendData(senderfield: EditText,
                         receiverfield: EditText,
                         typefield: Spinner,
                         dest1field: Spinner,
                         dest2field: Spinner,
                         password: String,
                         txv: TextView,
                         connect: ClientConn){
        val data = buildString {
            append(senderfield.getText().toString())
            append(",")
            append(receiverfield.getText().toString())
            append(",")
            append(password)
            append(",")
            append(typefield.selectedItem.toString())
            append(",")
            append(dest1field.selectedItem.toString())
            append(",")
            append(dest2field.selectedItem.toString())
        }
        txv.text = data
        connect.write(data.toByteArray())
    }

    private fun makepass(): String{
        var pass = ""
        for(i in 1..4) pass += (0..9).random().toString()
        return pass
    }

    private fun socketConnect() {

//            findNavController().navigate(R.id.action_navigation_home_to_response_fragment)

    }
}