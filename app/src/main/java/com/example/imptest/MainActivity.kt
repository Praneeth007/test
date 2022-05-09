package com.example.imptest

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.imptest.api.DBHelper
import com.example.imptest.ui.cases.CasesFragment
import com.example.myapplication.api.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var navView: BottomNavigationView
    private var progressDialog: ProgressDialog? = null
    private val dataSync: DataViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         navView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        if (isNetworkConnected())
            getData()
    }

     fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun getData() {
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog?.setTitle("Loading")
        progressDialog?.setMessage("Application is loading, please wait")
        progressDialog?.show()
        dataSync.fetchData().observe(this, { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {

                    val results = result.data as TotalResponse
                    Toast.makeText(
                        this@MainActivity,
                        results.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    saveData(results.statewise, results.casesTimeSeries, results.tested)

                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.LOADING -> {
                }
            }
        })
    }

    private fun saveData(
        statewise: List<Statewise>,
        casesTimeSeries: List<CasesTimeSery>,
        tested: List<Tested>
    ) {
        val db = DBHelper(this, null)

        db.deleteTables()

        statewise.forEach {
            db.addStateWise(it.state, it.active, it.recovered, it.deaths, 0)
        }
        casesTimeSeries.forEach {
            db.addCaseWise(it.date, it.dailyconfirmed, it.dailydeceased, it.dailyrecovered, 0)
        }
        tested.forEach {
            db.addTestWise(
                it.testedasof, it.dailyrtpcrsamplescollectedicmrapplication, it.samplereportedtoday,
                it.totaldosesadministered, it.source, 0
            )
        }
        progressDialog?.dismiss()
        navView.setSelectedItemId(R.id.navigation_home)

    }
}
