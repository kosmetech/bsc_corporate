package com.kosme.bsc_corporate.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kosme.bsc_corporate.R

class AkunFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    companion object {
        fun newInstance(): AkunFragment {
            val fragment = AkunFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}