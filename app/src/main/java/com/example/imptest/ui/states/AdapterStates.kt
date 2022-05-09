package com.example.imptest.ui.states

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

internal class AdapterStates(private var statesList: List<DBHelper.StateDetails>, var activity: FragmentActivity?) :
    RecyclerView.Adapter<AdapterStates.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var state: TextView = view.findViewById(R.id.dateET)
        var active: TextView = view.findViewById(R.id.confirmedEt)
        var recovered: TextView = view.findViewById(R.id.deceasedEt)
        var deaths: TextView = view.findViewById(R.id.recoveredEt)
        var imageView : ImageView = view.findViewById(R.id.iv)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.state_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val states = statesList[position]
        holder.state.text =states.state
        holder.active.text =states.active
        holder.recovered.text =states.recovered
        holder.deaths.text =states.deaths
        if (states.flag.toInt() == 1) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_star)
        }else{
            holder.imageView.setImageResource(R.drawable.ic_baseline_star_border)
        }
        holder.imageView.setOnClickListener {
            val db = activity?.let { it1 -> DBHelper(it1, null) }
            if (states.flag.toInt() == 1) {
                db?.updateQuery(DBHelper.TABLE_NAME,0,states.id.toInt(),object : DBHelper.DoneCallback {
                    override fun done() {
                        if ((activity as MainActivity?)?.isNetworkConnected() == true)
                        statesList = db.getStateWiseDetails()
                        else statesList = db.getStateWiseDetailsOffline()
                        notifyDataSetChanged()
                    }
                })
            }else{
                db?.updateQuery(DBHelper.TABLE_NAME,1,states.id.toInt(),object : DBHelper.DoneCallback {
                    override fun done() {
                        if ((activity as MainActivity?)?.isNetworkConnected() == true)
                            statesList = db.getStateWiseDetails()
                        else statesList = db.getStateWiseDetailsOffline()
                        notifyDataSetChanged()
                    }
                })
            }

        }
    }
    override fun getItemCount(): Int {
        return statesList.size
    }
}
