package com.kosme.bsc_corporate.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kosme.bsc_corporate.R
import com.kosme.bsc_corporate.data.CekDataResponse
import android.os.Bundle
import android.util.Log
import android.widget.*


class CekDataAdapter(private val list: List<CekDataResponse>) :
    RecyclerView.Adapter<CekDataAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
 // Define click listener for the ViewHolder's View.
            val title : TextView = view.findViewById(R.id.txt_title)
            val time : TextView = view.findViewById(R.id.txt_time)
            val target : TextView = view.findViewById(R.id.txt_percentage)
            val kpi : TextView = view.findViewById(R.id.txt_percentage_info)
            val nilai : TextView = view.findViewById(R.id.txt_nilai_angka)
        val textNilai : TextView = view.findViewById(R.id.txt_nilai)
        val download : ImageView = view.findViewById(R.id.btn_download)
        val layout : RelativeLayout = view.findViewById(R.id.layout)

        fun bindUi(data : CekDataResponse){

            title.text = data.kategori
            time.text = data.tanggal_isi
            target.text = data.target
            kpi.text = data.kpi
            nilai.text = Math.round(data.score!!).toString() + "%"
            if(data.file!!.length > 11){
                download.visibility = View.VISIBLE
                download.setOnClickListener {
                    val intent = Intent(itemView.context, PdfViewActivity::class.java)
                    intent.putExtra("url", data.file)
                    itemView.context.startActivity(intent)
                }
            } else {
                download.visibility = View.GONE
            }

            when(data.id_kategori){
                "kt1" -> layout.setBackgroundResource(R.drawable.bg_finansial)
                "kt2" -> layout.setBackgroundResource(R.drawable.bg_customer)
                "kt3" -> {
                    layout.setBackgroundResource(R.drawable.bg_internal)
                    title.setTextColor(Color.parseColor("#707070"))
                    time.setTextColor(Color.parseColor("#707070"))
                    target.setTextColor(Color.parseColor("#707070"))
                    kpi.setTextColor(Color.parseColor("#707070"))
                    nilai.setTextColor(Color.parseColor("#707070"))
                    download.setImageResource(R.drawable.ic_download_button_black)
                    textNilai.setTextColor(Color.parseColor("#707070"))
                }
                else -> layout.setBackgroundResource(R.drawable.bg_learningg)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_data, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        list[position]?.let { viewHolder.bindUi(it) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size



}