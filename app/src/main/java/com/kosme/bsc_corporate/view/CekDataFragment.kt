package com.kosme.bsc_corporate.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kosme.bsc_corporate.R
import com.kosme.bsc_corporate.api.SkorService
import com.kosme.bsc_corporate.data.CekDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter
import android.widget.AdapterView







class CekDataFragment : Fragment() {

    var tanggal : String = ""
    val baseURL = "https://kosmeproduct.com/bsc/"
    var listData : List<CekDataResponse>? = null
    var categories = arrayOf("ALL", "FINANCIAL", "CUSTOMER", "INTERNAL BISNIS", "LEARNING & GROWTH")
    var categoryAdapter : ArrayAdapter<*>? = null
    var categoryString : String = "ALL"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cek_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = view.findViewById<EditText>(R.id.filter_date)
        val rv_item = view.findViewById<RecyclerView>(R.id.rv_item)
        val filter_category = view.findViewById<Spinner>(R.id.filter_category)

        val cal = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = format.format(Date())
        filter.setText(currentDate, TextView.BufferType.EDITABLE)

        getData(currentDate, rv_item, categoryString)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            filter.setText(sdf.format(cal.time), TextView.BufferType.EDITABLE)
            tanggal = sdf.format(cal.time)
            getData(tanggal, rv_item, categoryString)
            }

        filter.setOnClickListener {
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        listData = ArrayList()
        categoryAdapter = ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_list_item_1, categories)
        filter_category.adapter = categoryAdapter

        filter_category.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                categoryString = parent.getItemAtPosition(position) as String
                filter_category.setSelection(position)
                getData(tanggal, rv_item, categoryString)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }

    fun getData(tanggal : String, rv : RecyclerView, filter : String){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SkorService::class.java)
        val call = service.getData(tanggal)
        call!!.enqueue(object : Callback<List<CekDataResponse>>{
            override fun onResponse(
                call: Call<List<CekDataResponse>>,
                response: Response<List<CekDataResponse>>
            ) {
                val cekData = response.body()

                if (cekData.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "0 Data", Toast.LENGTH_SHORT)
                } else {
                    if (filter == "ALL"){
                        var adapter = CekDataAdapter(cekData)
                        var layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        rv.layoutManager = layoutManager
                        rv.adapter = adapter
                        rv.setHasFixedSize(false)
                    } else {
                        val mutablelist = mutableListOf<CekDataResponse>()
                        cekData?.forEach{
                            if (it.kategori == filter){
                                mutablelist.add(it)
                                var adapter = CekDataAdapter(mutablelist)
                                var layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                rv.layoutManager = layoutManager
                                rv.adapter = adapter
                                rv.setHasFixedSize(false)
                            }
                    }
                }}
            }

            override fun onFailure(call: Call<List<CekDataResponse>>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT)
            }
        })


    }

    companion object {
        fun newInstance(): CekDataFragment {
            val fragment = CekDataFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}