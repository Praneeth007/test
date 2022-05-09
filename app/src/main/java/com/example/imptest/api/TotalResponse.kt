package com.example.myapplication.api
import com.google.gson.annotations.SerializedName

data class TotalResponse(
    @SerializedName("cases_time_series")
    val casesTimeSeries: List<CasesTimeSery>,
    @SerializedName("statewise")
    val statewise: List<Statewise>,
    @SerializedName("tested")
    val tested: List<Tested>
)

data class CasesTimeSery(
    @SerializedName("dailyconfirmed")
    val dailyconfirmed: String,
    @SerializedName("dailydeceased")
    val dailydeceased: String,
    @SerializedName("dailyrecovered")
    val dailyrecovered: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("dateymd")
    val dateymd: String,
    @SerializedName("totalconfirmed")
    val totalconfirmed: String,
    @SerializedName("totaldeceased")
    val totaldeceased: String,
    @SerializedName("totalrecovered")
    val totalrecovered: String
)

data class Statewise(
    @SerializedName("active")
    val active: String,
    @SerializedName("confirmed")
    val confirmed: String,
    @SerializedName("deaths")
    val deaths: String,
    @SerializedName("deltaconfirmed")
    val deltaconfirmed: String,
    @SerializedName("deltadeaths")
    val deltadeaths: String,
    @SerializedName("deltarecovered")
    val deltarecovered: String,
    @SerializedName("lastupdatedtime")
    val lastupdatedtime: String,
    @SerializedName("migratedother")
    val migratedother: String,
    @SerializedName("recovered")
    val recovered: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("statecode")
    val statecode: String,
    @SerializedName("statenotes")
    val statenotes: String
){
    var selected: Boolean = false
}

data class Tested(
    @SerializedName("dailyrtpcrsamplescollectedicmrapplication")
    val dailyrtpcrsamplescollectedicmrapplication: String,
    @SerializedName("firstdoseadministered")
    val firstdoseadministered: String,
    @SerializedName("frontlineworkersvaccinated1stdose")
    val frontlineworkersvaccinated1stdose: String,
    @SerializedName("frontlineworkersvaccinated2nddose")
    val frontlineworkersvaccinated2nddose: String,
    @SerializedName("healthcareworkersvaccinated1stdose")
    val healthcareworkersvaccinated1stdose: String,
    @SerializedName("healthcareworkersvaccinated2nddose")
    val healthcareworkersvaccinated2nddose: String,
    @SerializedName("over45years1stdose")
    val over45years1stdose: String,
    @SerializedName("over45years2nddose")
    val over45years2nddose: String,
    @SerializedName("over60years1stdose")
    val over60years1stdose: String,
    @SerializedName("over60years2nddose")
    val over60years2nddose: String,
    @SerializedName("positivecasesfromsamplesreported")
    val positivecasesfromsamplesreported: String,
    @SerializedName("registration18-45years")
    val registration1845years: String,
    @SerializedName("registrationabove45years")
    val registrationabove45years: String,
    @SerializedName("registrationflwhcw")
    val registrationflwhcw: String,
    @SerializedName("registrationonline")
    val registrationonline: String,
    @SerializedName("registrationonspot")
    val registrationonspot: String,
    @SerializedName("samplereportedtoday")
    val samplereportedtoday: String,
    @SerializedName("seconddoseadministered")
    val seconddoseadministered: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("source2")
    val source2: String,
    @SerializedName("source3")
    val source3: String,
    @SerializedName("source4")
    val source4: String,
    @SerializedName("testedasof")
    val testedasof: String,
    @SerializedName("testsconductedbyprivatelabs")
    val testsconductedbyprivatelabs: String,
    @SerializedName("to60yearswithco-morbidities1stdose")
    val to60yearswithcoMorbidities1stdose: String,
    @SerializedName("to60yearswithco-morbidities2nddose")
    val to60yearswithcoMorbidities2nddose: String,
    @SerializedName("totaldosesadministered")
    val totaldosesadministered: String,
    @SerializedName("totaldosesavailablewithstates")
    val totaldosesavailablewithstates: String,
    @SerializedName("totaldosesavailablewithstatesprivatehospitals")
    val totaldosesavailablewithstatesprivatehospitals: String,
    @SerializedName("totaldosesinpipeline")
    val totaldosesinpipeline: String,
    @SerializedName("totaldosesprovidedtostatesuts")
    val totaldosesprovidedtostatesuts: String,
    @SerializedName("totalindividualsregistered")
    val totalindividualsregistered: String,
    @SerializedName("totalindividualstested")
    val totalindividualstested: String,
    @SerializedName("totalpositivecases")
    val totalpositivecases: String,
    @SerializedName("totalrtpcrsamplescollectedicmrapplication")
    val totalrtpcrsamplescollectedicmrapplication: String,
    @SerializedName("totalsamplestested")
    val totalsamplestested: String,
    @SerializedName("totalsessionsconducted")
    val totalsessionsconducted: String,
    @SerializedName("totalvaccineconsumptionincludingwastage")
    val totalvaccineconsumptionincludingwastage: String,
    @SerializedName("updatetimestamp")
    val updatetimestamp: String,
    @SerializedName("years1stdose")
    val years1stdose: String,
    @SerializedName("years2nddose")
    val years2nddose: String
)