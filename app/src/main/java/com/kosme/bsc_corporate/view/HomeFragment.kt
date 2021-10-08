package com.kosme.bsc_corporate.view

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.kosme.bsc_corporate.R
import com.kosme.bsc_corporate.api.SkorService
import com.kosme.bsc_corporate.data.ResponseItem
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.timqi.sectorprogressview.ColorfulRingProgressView


class HomeFragment : Fragment() {

    var tanggal : String = ""
    val baseURL = "https://kosmeproduct.com/bsc/"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = view.findViewById<EditText>(R.id.filter_date)

        val txt_finansial = view.findViewById<TextView>(R.id.txt_percentage_financial)
        val txt_customer = view.findViewById<TextView>(R.id.txt_percentage_customer)
        val txt_bisnis = view.findViewById<TextView>(R.id.txt_percentage_bisnis)
        val txt_learning = view.findViewById<TextView>(R.id.txt_percentage_learning)

        val txt_avg_finansial = view.findViewById<TextView>(R.id.txt_avg_financial)
        val txt_avg_customer = view.findViewById<TextView>(R.id.txt_avg_customer)
        val txt_avg_bisnis = view.findViewById<TextView>(R.id.txt_avg_bisnis)
        val txt_avg_learning = view.findViewById<TextView>(R.id.txt_avg_learning)

        val menu_financial = view.findViewById<LinearLayout>(R.id.menu_finansial)
        val menu_customer = view.findViewById<LinearLayout>(R.id.menu_customer)
        val menu_bisnis = view.findViewById<LinearLayout>(R.id.menu_bisnis)
        val menu_learning = view.findViewById<LinearLayout>(R.id.menu_learning)

        val img_financial = view.findViewById<ImageView>(R.id.img_financial)
        val img_customer = view.findViewById<ImageView>(R.id.img_customer)
        val img_bisnis = view.findViewById<ImageView>(R.id.img_internal_bisnis)
        val img_learning = view.findViewById<ImageView>(R.id.img_learning)

        var donut = view.findViewById<ColorfulRingProgressView>(R.id.donut)
        val total = view.findViewById<TextView>(R.id.text_total)
        val persen = view.findViewById<TextView>(R.id.txt_percentage)


        var cal = Calendar.getInstance()

        val format = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = format.format(Date())
        filter.setText(currentDate, TextView.BufferType.EDITABLE)

        getData(currentDate, txt_finansial, txt_customer, txt_bisnis, txt_learning, txt_avg_finansial, txt_avg_customer, txt_avg_bisnis, txt_avg_learning, menu_financial, menu_customer, menu_bisnis, menu_learning, img_financial, img_customer, img_bisnis, img_learning, donut, total, persen)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            filter.setText(sdf.format(cal.time), TextView.BufferType.EDITABLE)
            tanggal = sdf.format(cal.time)
            getData(tanggal, txt_finansial, txt_customer, txt_bisnis, txt_learning, txt_avg_finansial, txt_avg_customer, txt_avg_bisnis, txt_avg_learning, menu_financial, menu_customer, menu_bisnis, menu_learning, img_financial, img_customer, img_bisnis, img_learning, donut, total, persen)
        }

        filter.setOnClickListener {
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        menu_financial.setOnClickListener {
            val i = Intent(
                requireActivity().baseContext,
                DialogGrafik::class.java
            )
            i.putExtra("KEY_CATEGORY", "kt1")
            requireActivity().startActivity(i)
        }
        menu_customer.setOnClickListener {
            val i = Intent(
                requireActivity().baseContext,
                DialogGrafik::class.java
            )
            i.putExtra("KEY_CATEGORY", "kt2")
            requireActivity().startActivity(i)
        }
        menu_bisnis.setOnClickListener {
            val i = Intent(
                requireActivity().baseContext,
                DialogGrafik::class.java
            )
            i.putExtra("KEY_CATEGORY", "kt3")
            requireActivity().startActivity(i)
        }
        menu_learning.setOnClickListener {
            val i = Intent(
                requireActivity().baseContext,
                DialogGrafik::class.java
            )
            i.putExtra("KEY_CATEGORY", "kt4")
            requireActivity().startActivity(i)
        }

    }

    fun getData(tanggal : String, txt_finansial : TextView, txt_customer : TextView, txt_bisnis : TextView, txt_learning : TextView,
                txt_avg_finansial : TextView, txt_avg_customer : TextView, txt_avg_bisnis : TextView, txt_avg_learning : TextView,
                menu_finansial : LinearLayout, menu_customer : LinearLayout, menu_bisnis : LinearLayout, menu_learning : LinearLayout,
                img_financial : ImageView, img_customer : ImageView, img_bisnis : ImageView, img_learning : ImageView,
    donut : ColorfulRingProgressView, total : TextView, persen : TextView){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SkorService::class.java)
        val call = service.getSkor(tanggal)
        call!!.enqueue(object : Callback<List<ResponseItem>> {
            override fun onResponse(call: Call<List<ResponseItem>>, response: Response<List<ResponseItem>>) {

                val skorData = response.body()
//                if (skorData.isNullOrEmpty()){
//                    txt_avg_finansial.text = "0/20"
//                    txt_finansial.text = "0%"
//                    menu_finansial.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//
//                    txt_avg_customer.text = "0/30"
//                    txt_customer.text = "0%"
//                    menu_customer.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//
//                    txt_avg_bisnis.text = "0/20"
//                    txt_bisnis.text = "0%"
//                    menu_bisnis.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//
//                    txt_avg_learning.text = "0/30"
//                    txt_learning.text = "0%"
//                    menu_learning.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                } else {
//
//                    var financial : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt1" }
//                    var customer : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt2" }
//                    var bisnis : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt3" }
//                    var learning : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt4" }
//
//                    //financial
//                    if (financial.isNotEmpty()){
//                        val persen = Math.round(financial[0].jumlah_perkategori!! * 100.0)/financial[0].bagi_kategori!!
//                        txt_avg_finansial.text = financial[0].jumlah_perkategori.toString() + "/" + financial[0].bagi_kategori.toString()
//                        txt_finansial.text = persen.toString() + "%"
//                        Log.d("---", persen.toString())
//
//                        when(persen){
//                            in 0..40 -> {
//                                menu_finansial.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 41..50 -> {
//                                menu_finansial.background.setColorFilter(Color.parseColor("#F5A962"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 51..70 -> {
//                                menu_finansial.background.setColorFilter(Color.parseColor("#FFC93C"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 71..90 -> {
//                                menu_finansial.background.setColorFilter(Color.parseColor("#36815E"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 91..100 -> {
//                                menu_finansial.background.setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                        }
//                    } else {
//                        txt_avg_finansial.text = "0/20"
//                        txt_finansial.text = "0%"
//                        menu_finansial.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                    }
//
//                    //customer
//                    if (customer.isNotEmpty()){
//                        val persen = Math.round(customer[0].jumlah_perkategori!! * 100.0)/customer[0].bagi_kategori!!
//                        txt_avg_customer.text = customer[0].jumlah_perkategori.toString() + "/" + customer[0].bagi_kategori.toString()
//                        txt_customer.text = persen.toString() + "%"
//                        when(persen){
//                            in 0..40 -> {
//                                menu_customer.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 41..50 -> {
//                                menu_customer.background.setColorFilter(Color.parseColor("#F5A962"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 51..70 -> {
//                                menu_customer.background.setColorFilter(Color.parseColor("#FFC93C"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 71..90 -> {
//                                menu_customer.background.setColorFilter(Color.parseColor("#36815E"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 91..100 -> {
//                                menu_customer.background.setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                        }
//                    } else {
//                        txt_avg_customer.text = "0/30"
//                        txt_customer.text = "0%"
//                        menu_customer.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                    }
//
//                    //bisnis
//                    if (bisnis.isNotEmpty()){
//                        val persen = Math.round(bisnis[0].jumlah_perkategori!! * 100.0)/bisnis[0].bagi_kategori!!
//                        txt_avg_bisnis.text = bisnis[0].jumlah_perkategori.toString() + "/" + bisnis[0].bagi_kategori.toString()
//                        txt_bisnis.text = persen.toString() + "%"
//                        when(persen){
//                            in 0..40 -> {
//                                menu_bisnis.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 41..50 -> {
//                                menu_bisnis.background.setColorFilter(Color.parseColor("#F5A962"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 51..70 -> {
//                                menu_bisnis.background.setColorFilter(Color.parseColor("#FFC93C"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 71..90 -> {
//                                menu_bisnis.background.setColorFilter(Color.parseColor("#36815E"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 91..100 -> {
//                                menu_bisnis.background.setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                        }
//                    } else {
//                        txt_avg_bisnis.text = "0/20"
//                        txt_bisnis.text = "0%"
//                        menu_bisnis.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                    }
//
//                    //learning
//                    if (learning.isNotEmpty()){
//                        val persen = Math.round(learning[0].jumlah_perkategori!! * 100.0)/learning[0].bagi_kategori!!
//                        txt_avg_learning.text = learning[0].jumlah_perkategori.toString() + "/" + learning[0].bagi_kategori.toString()
//                        txt_learning.text = persen.toString() + "%"
//                        when(persen){
//                            in 0..40 -> {
//                                menu_learning.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 41..50 -> {
//                                menu_learning.background.setColorFilter(Color.parseColor("#F5A962"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 51..70 -> {
//                                menu_learning.background.setColorFilter(Color.parseColor("#FFC93C"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 71..90 -> {
//                                menu_learning.background.setColorFilter(Color.parseColor("#36815E"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                            in 91..100 -> {
//                                menu_learning.background.setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.SRC_ATOP)
//                            }
//                        }
//                    } else {
//                        txt_avg_learning.text = "0/30"
//                        txt_learning.text = "0%"
//                        menu_learning.background.setColorFilter(Color.parseColor("#D44000"), PorterDuff.Mode.SRC_ATOP)
//                    }
//                }


                if (skorData.isNullOrEmpty()){
                    txt_avg_finansial.text = "0/20"
                    txt_finansial.text = "0%"
                    img_financial.setImageResource(R.drawable.ic_financial_very_poor)

                    txt_avg_customer.text = "0/30"
                    txt_customer.text = "0%"
                    img_customer.setImageResource(R.drawable.ic_customer_very_poor)

                    txt_avg_bisnis.text = "0/20"
                    txt_bisnis.text = "0%"
                    img_bisnis.setImageResource(R.drawable.ic_bisnis_very_poor)

                    txt_avg_learning.text = "0/30"
                    txt_learning.text = "0%"
                    img_learning.setImageResource(R.drawable.ic_learning_very_poor)
                } else {

                    var financial : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt1" }
                    var customer : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt2" }
                    var bisnis : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt3" }
                    var learning : List<ResponseItem> = skorData!!.filter { it.id_kategori == "kt4" }
                    val sum = skorData.sumBy { Math.round(it.jumlah_perkategori!!) }
                    donut.percent = sum.toFloat()
                    persen.text = sum.toString() + "%"
                    total.text = skorData.sumBy { Math.round(it.jumlah_perkategori!!) }.toString() + "/100"

                    //financial
                    if (financial.isNotEmpty()){
                        val persen = Math.round(financial[0].jumlah_perkategori!! * 100.0)/financial[0].bagi_kategori!!
                        txt_avg_finansial.text = Math.round(financial[0].jumlah_perkategori!!.toFloat()).toString() + "/" + financial[0].bagi_kategori.toString()
                        txt_finansial.text = persen.toString() + "%"

                        when(persen){
                            in 0..40 -> {
                                txt_finansial.setTextColor(Color.parseColor("#D44000"))
                                img_financial.setImageResource(R.drawable.ic_financial_very_poor)
                            }
                            in 41..50 -> {
                                txt_finansial.setTextColor(Color.parseColor("#F5A962"))
                                img_financial.setImageResource(R.drawable.ic_financial_poor)
                            }
                            in 51..70 -> {
                                txt_finansial.setTextColor(Color.parseColor("#FFC93C"))
                                img_financial.setImageResource(R.drawable.ic_financial_enough)
                            }
                            in 71..90 -> {
                                txt_finansial.setTextColor(Color.parseColor("#36815E"))
                                img_financial.setImageResource(R.drawable.ic_financial_good_enough)
                            }
                            in 91..100 -> {
                                txt_finansial.setTextColor(Color.parseColor("#141414"))
                                img_financial.setImageResource(R.drawable.ic_financial_excellent)
                            }
                        }
                    } else {
                        txt_avg_finansial.text = "0/20"
                        txt_finansial.text = "0%"
                        img_financial.setImageResource(R.drawable.ic_financial_very_poor)
                    }

                    //customer
                    if (customer.isNotEmpty()){
                        val persen = Math.round(customer[0].jumlah_perkategori!! * 100.0)/customer[0].bagi_kategori!!
                        txt_avg_customer.text = Math.round(customer[0].jumlah_perkategori!!.toFloat()).toString() + "/" + customer[0].bagi_kategori.toString()
                        txt_customer.text = persen.toString() + "%"
                        when(persen){
                            in 0..40 -> {
                                txt_customer.setTextColor(Color.parseColor("#D44000"))
                                img_customer.setImageResource(R.drawable.ic_customer_very_poor)
                            }
                            in 41..50 -> {
                                txt_customer.setTextColor(Color.parseColor("#F5A962"))
                                img_customer.setImageResource(R.drawable.ic_customer_poor)
                            }
                            in 51..70 -> {
                                txt_customer.setTextColor(Color.parseColor("#FFC93C"))
                                img_customer.setImageResource(R.drawable.ic_customer_enough)
                            }
                            in 71..90 -> {
                                txt_customer.setTextColor(Color.parseColor("#36815E"))
                                img_customer.setImageResource(R.drawable.ic_customer_good_enough)
                            }
                            in 91..100 -> {
                                txt_customer.setTextColor(Color.parseColor("#141414"))
                                img_customer.setImageResource(R.drawable.ic_customer_excellent)
                            }
                        }
                    } else {
                        txt_avg_customer.text = "0/30"
                        txt_customer.text = "0%"
                        img_customer.setImageResource(R.drawable.ic_customer_very_poor)
                    }

                    //bisnis
                    if (bisnis.isNotEmpty()){
                        val persen = Math.round(bisnis[0].jumlah_perkategori!! * 100.0)/bisnis[0].bagi_kategori!!
                        txt_avg_bisnis.text = Math.round(bisnis[0].jumlah_perkategori!!.toFloat()).toString() + "/" + bisnis[0].bagi_kategori.toString()
                        txt_bisnis.text = persen.toString() + "%"
                        when(persen){
                            in 0..40 -> {
                                txt_bisnis.setTextColor(Color.parseColor("#D44000"))
                                img_bisnis.setImageResource(R.drawable.ic_bisnis_very_poor)
                            }
                            in 41..50 -> {
                                txt_bisnis.setTextColor(Color.parseColor("#F5A962"))
                                img_bisnis.setImageResource(R.drawable.ic_bisnis_poor)
                            }
                            in 51..70 -> {
                                txt_bisnis.setTextColor(Color.parseColor("#FFC93C"))
                                img_bisnis.setImageResource(R.drawable.ic_bisnis_enough)
                            }
                            in 71..90 -> {
                                txt_bisnis.setTextColor(Color.parseColor("#36815E"))
                                img_bisnis.setImageResource(R.drawable.ic_bisnis_good_enough)
                            }
                            in 91..100 -> {
                                txt_bisnis.setTextColor(Color.parseColor("#141414"))
                                img_bisnis.setImageResource(R.drawable.ic_bisnis_excellent)
                            }
                        }
                    } else {
                        txt_avg_bisnis.text = "0/20"
                        txt_bisnis.text = "0%"
                        img_bisnis.setImageResource(R.drawable.ic_bisnis_very_poor)
                    }

                    //learning
                    if (learning.isNotEmpty()){
                        val persen = Math.round(learning[0].jumlah_perkategori!! * 100.0)/learning[0].bagi_kategori!!
                        txt_avg_learning.text = Math.round(learning[0].jumlah_perkategori!!.toFloat()).toString() + "/" + learning[0].bagi_kategori.toString()
                        txt_learning.text = persen.toString() + "%"
                        when(persen){
                            in 0..40 -> {
                                txt_learning.setTextColor(Color.parseColor("#D44000"))
                                img_learning.setImageResource(R.drawable.ic_learning_very_poor)
                            }
                            in 41..50 -> {
                                txt_learning.setTextColor(Color.parseColor("#F5A962"))
                                img_learning.setImageResource(R.drawable.ic_learning_poor)
                            }
                            in 51..70 -> {
                                txt_learning.setTextColor(Color.parseColor("#FFC93C"))
                                img_learning.setImageResource(R.drawable.ic_learning_enough)
                            }
                            in 71..90 -> {
                                txt_learning.setTextColor(Color.parseColor("#36815E"))
                                img_learning.setImageResource(R.drawable.ic_learning_good_enough)
                            }
                            in 91..100 -> {
                                txt_learning.setTextColor(Color.parseColor("#141414"))
                                img_learning.setImageResource(R.drawable.ic_learning_excellent)
                            }
                        }
                    } else {
                        txt_avg_learning.text = "0/30"
                        txt_learning.text = "0%"
                        img_learning.setImageResource(R.drawable.ic_learning_very_poor)
                    }
                }
            }

            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                Log.d("---", t.localizedMessage)
            }
        })
    }

    fun percentage(jumlah : Int, bagi: Int) : Int{
        val persen = Math.round(jumlah/bagi.toDouble())*100.0
        return persen.toInt()
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
