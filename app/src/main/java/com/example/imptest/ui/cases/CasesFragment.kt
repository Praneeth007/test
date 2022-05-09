package com.example.imptest.ui.cases

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

class CasesFragment : Fragment() {

    private var list: MutableList<DBHelper.CaseDetails>?=null
    lateinit var casesRv:RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        casesRv= root.findViewById(R.id.casesRv)

        return root
    }

    override fun onResume() {
        super.onResume()
        getDetails()
    }

    fun getDetails(){
        val db = DBHelper(requireContext(), null)
        if ((activity as MainActivity?)?.isNetworkConnected() == true)
         list = db.getCaseWiseDetails()
        else  list = db.getCaseWiseDetailsOffline()
        casesRv.apply {
            layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = AdapterCases(list!!,activity)
        }
    }
}