package com.kosme.bsc_corporate.view

import android.R.attr
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.view.WindowManager

import android.view.ViewGroup

import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.kosme.bsc_corporate.R
import com.kosme.bsc_corporate.api.SkorService
import com.kosme.bsc_corporate.data.DataGrafik
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import com.anychart.chart.common.dataentry.ValueDataEntry

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.AnyChart.column
import com.anychart.enums.Anchor
import com.anychart.enums.Position
import com.anychart.AnyChart.cartesian

import com.anychart.enums.HoverMode

import com.anychart.enums.TooltipPositionMode
import android.content.Intent
import android.os.Build
import android.util.Log
import com.anychart.charts.Cartesian


class DialogGrafik : AppCompatActivity() {

    companion object {

        const val TAG = "Dialog"

        private const val KEY_CATEGORY = "KEY_CATEGORY"
        val baseURL = "https://kosmeproduct.com/bsc/"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_grafik)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val startDate = findViewById<EditText>(R.id.start_date)
        val endDate = findViewById<EditText>(R.id.end_date)
        val submit = findViewById<Button>(R.id.btn_submit)
        val chart = findViewById<AnyChartView>(R.id.chart)
        val i = intent
        val cat = i.getStringExtra(KEY_CATEGORY)
        var dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        endDate.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, month, dayOfMonth -> //todo
                    val newDate = Calendar.getInstance()
                    newDate[year, month] = dayOfMonth
                    endDate.setText(dateFormatter.format(newDate.time))
                },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            val max = dateFormatter.parse(startDate.getText().toString().trim())
            val min = dateFormatter.parse(startDate.getText().toString().trim())
            val c = Calendar.getInstance()
            c.time = max
            c.add(Calendar.DAY_OF_YEAR, +7)
            val newDate = c.timeInMillis
            datePickerDialog.getDatePicker().setMaxDate(newDate)

            val millis = min.time
            datePickerDialog.getDatePicker().setMinDate(millis)
            datePickerDialog.show()
        })
        startDate.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, month, dayOfMonth -> //todo
                    val newDate = Calendar.getInstance()
                    newDate[year, month] = dayOfMonth
                    startDate.setText(dateFormatter.format(newDate.time))
                },
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )

            datePickerDialog.show()
        })

        submit.setOnClickListener(View.OnClickListener {
            val filterend = endDate.getText().toString().trim()
            val filterstart = startDate.getText().toString().trim()
            getData(filterstart, filterend, cat!!, chart)
        })
    }

    private fun setupView(view: View) {


    }

    fun getData(startDate : String, endDate : String, category: String, chart: AnyChartView){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SkorService::class.java)
        val call = service.getGrafik(category, startDate, endDate)
        call!!.enqueue(object : Callback<List<DataGrafik>> {
            override fun onResponse(
                call: Call<List<DataGrafik>>,
                response: Response<List<DataGrafik>>
            ) {
                val cekData = response.body()


                if (cekData.isNullOrEmpty()){
                    Toast.makeText(this@DialogGrafik, "0 Data", Toast.LENGTH_SHORT)
                } else {

                    var date = cekData!!.map { it -> it.tanggal_isi } as List<String>
                    var percentage = cekData!!.map { it -> Math.round(it.persen!!) } as List<Int>
                    val cartesian: Cartesian = column()
                    val data: MutableList<DataEntry> = ArrayList()
                    for (i in 0..date.size - 1){
                        data.add(ValueDataEntry(date[i] , percentage[i]))
                    }

                    val column = cartesian.column(data)
                    column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0.0)
                        .offsetY(7.0)
                        .format("")

                    column.fill("#FFDAC7")
                    cartesian.animation(true)

                    cartesian.yScale().minimum(0.0)

                    cartesian.yAxis(0).labels().format("")
                    cartesian.xAxis(0).labels().rotation(45)

                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
                    cartesian.interactivity().hoverMode(HoverMode.BY_X)

                    cartesian.xAxis(0).title("Date")
                    cartesian.yAxis(0).title("Percentage")

                    chart.setChart(cartesian)

                }
            }

            override fun onFailure(call: Call<List<DataGrafik>>, t: Throwable) {
                Toast.makeText(this@DialogGrafik, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}