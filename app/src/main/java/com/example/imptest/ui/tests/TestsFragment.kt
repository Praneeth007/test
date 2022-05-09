package com.example.imptest.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imptest.MainActivity
import com.example.imptest.R
import com.example.imptest.api.DBHelper

class TestsFragment : Fragment() {

    private var list: MutableList<DBHelper.TestDetails>?=null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        val db = DBHelper(requireContext(), null)

        if ((activity as MainActivity?)?.isNetworkConnected() == true)
      list = db.getTestWiseDetails()
        else list = db.getTestWiseDetailsOffline()

        val testRv: RecyclerView = root.findViewById(R.id.testsRv)

        testRv.apply {
            layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterTests(list!!,activity)

        }
        return root
    }
}