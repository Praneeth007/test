package com.example.imptest.ui.states

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imptest.MainActivity
import com.example.imptest.R
import com.example.imptest.api.DBHelper
import com.example.myapplication.api.DataViewModel

class StatesFragment : Fragment() {

    private var list: MutableList<DBHelper.StateDetails>?=null
    private val dataSync: DataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        val db = DBHelper(requireContext(), null)
        if ((activity as MainActivity?)?.isNetworkConnected() == true)
         list = db.getStateWiseDetails()
        else list = db.getStateWiseDetailsOffline()
        val stateRv: RecyclerView = root.findViewById(R.id.stateRv)

        stateRv.apply {
            layoutManager= LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = AdapterStates(list!!, activity)
        }

        return root
    }

}