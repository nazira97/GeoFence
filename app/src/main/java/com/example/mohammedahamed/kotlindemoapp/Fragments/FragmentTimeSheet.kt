package com.example.mohammedahamed.kotlindemoapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mohammedahamed.kotlindemoapp.R

/**
 * Created By Nazira on 03/12/18
 */

/**
 * This fragment is used to add TimeSheet details
 */
class FragmentTimeSheet : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_time_sheet, container, false)
        return rootView
    }
}