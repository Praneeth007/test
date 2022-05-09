package com.example.imptest.ui.cases

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
import com.example.imptest.api.DBHelper.Companion.TABLE_NAME_CASES

internal class AdapterCases(
    private var caseList: List<DBHelper.CaseDetails>,
    var activity: FragmentActivity?
) :
    RecyclerView.Adapter<AdapterCases.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date: TextView = view.findViewById(R.id.dateET)
        var confirmed: TextView = view.findViewById(R.id.confirmedEt)
        var deceased: TextView = view.findViewById(R.id.deceasedEt)
        var recovered: TextView = view.findViewById(R.id.recoveredEt)
        var imageView :ImageView = view.findViewById(R.id.iv)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cases_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val case = caseList[position]
        holder.date.text =case.date
        holder.confirmed.text =case.totalconfirmed
        holder.deceased.text =case.totaldeceased
        holder.recovered.text =case.totalrecovered

        if (case.flag.toInt() == 1) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_star)
        }else{
            holder.imageView.setImageResource(R.drawable.ic_baseline_star_border)
        }
        holder.imageView.setOnClickListener {
            val db = activity?.let { it1 -> DBHelper(it1, null) }
            if (case.flag.toInt() == 1) {
                db?.updateQuery(TABLE_NAME_CASES,0,case.id.toInt(),object : DBHelper.DoneCallback {
                    override fun done() {
                        if ((activity as MainActivity?)?.isNetworkConnected() == true)
                        caseList = db.getCaseWiseDetails()
                        else caseList = db.getCaseWiseDetailsOffline()
                        notifyDataSetChanged()
                    }
                })
            }else{
                db?.updateQuery(TABLE_NAME_CASES,1,case.id.toInt(),object : DBHelper.DoneCallback {
                    override fun done() {
                        if ((activity as MainActivity?)?.isNetworkConnected() == true)
                            caseList = db.getCaseWiseDetails()
                        else caseList = db.getCaseWiseDetailsOffline()
                        notifyDataSetChanged()
                    }
                })
            }

        }

    }
    override fun getItemCount(): Int {
        return caseList.size
    }
}


