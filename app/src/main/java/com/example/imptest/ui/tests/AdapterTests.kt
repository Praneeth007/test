package com.example.imptest.ui.tests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.imptest.MainActivity
import com.example.imptest.R
import com.example.imptest.api.DBHelper

internal class AdapterTests(private var testList: List<DBHelper.TestDetails>, var activity: FragmentActivity?) :
    RecyclerView.Adapter<AdapterTests.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var testedas: TextView = view.findViewById(R.id.dateET)
        var dailyrtpc: TextView = view.findViewById(R.id.confirmedEt)
        var samplesreported: TextView = view.findViewById(R.id.deceasedEt)
        var totaldoes: TextView = view.findViewById(R.id.recoveredEt)
        var source: TextView = view.findViewById(R.id.sourceEt)
        var imageView : ImageView = view.findViewById(R.id.iv)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.test_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val test = testList[position]

        holder.testedas.text =test.testedasof
        holder.dailyrtpc.text =test.dailyrtpcrsamplescollectedicmrapplication
        holder.samplesreported.text =test.samplereportedtoday
        holder.totaldoes.text =test.totaldosesadministered
        holder.source.text =test.source


        if (test.flag.toInt() == 1) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_star)
        }else{
            holder.imageView.setImageResource(R.drawable.ic_baseline_star_border)
        }
        holder.imageView.setOnClickListener {
            val db = activity?.let { it1 -> DBHelper(it1, null) }
            if (test.flag.toInt() == 1) {
                db?.updateQuery(DBHelper.TABLE_NAME_TEST,0,test.id.toInt(),object : DBHelper.DoneCallback {
                    override fun done() {
                        if ((activity as MainActivity?)?.isNetworkConnected() == true)
                        testList = db.getTestWiseDetails()
                        else  testList = db.getTestWiseDetailsOffline()
                        notifyDataSetChanged()
                    }
                })
            }else{
                db?.updateQuery(DBHelper.TABLE_NAME_TEST,1,test.id.toInt(),object : DBHelper.DoneCallback {
                    override fun done() {
                        if ((activity as MainActivity?)?.isNetworkConnected() == true)
                            testList = db.getTestWiseDetails()
                        else  testList = db.getTestWiseDetailsOffline()
                        notifyDataSetChanged()
                    }
                })
            }

        }

    }
    override fun getItemCount(): Int {
        return testList.size
    }
}

