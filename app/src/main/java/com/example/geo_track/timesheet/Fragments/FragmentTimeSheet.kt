package com.example.geo_track.timesheet.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.data.repository.TimeSheetDataRepository
import com.example.domain.model.TimeSheetData
import com.example.domain.usecase.TimeSheetUseCase
import com.example.domain.util.AppConstants.TAG
import com.example.geo_track.timesheet.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_time_sheet.*
import kotlinx.android.synthetic.main.fragment_time_sheet.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.text.TextWatcher

/**
 * Created By Nazira on 03/12/18
 */

/**
 * This fragment is used to add TimeSheet details
 */
class FragmentTimeSheet : Fragment() {
    var str : String = ""
    var cal = Calendar.getInstance()
    private val timeSheetCase = object : TimeSheetUseCase(TimeSheetDataRepository()){}
    val projectData = object : TimeSheetData(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_time_sheet, container, false)

        rootView.btn_add.setOnClickListener {
              dynamicEditText()
          }
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        rootView.btn_date.setOnClickListener {
            DatePickerDialog(requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        rootView.btn_done.setOnClickListener {
            var addingTimeSheet = timeSheetCase.storeTimeSheetData(requireContext(), projectData)
            addingTimeSheet
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { storeTime ->
                                if (storeTime) {
                                    Log.d(TAG, "TimeSheetData Stored")

                                    projectData.projectCode = str
                                    Log.e(TAG,projectData.projectCode);
                                    Toast.makeText(requireContext(), "TimeSheetData Stored" + " " + projectData.date + " " + projectData.projectCode, Toast.LENGTH_LONG).show()
                                }
                            },
                            { error ->
                                Log.e("Error", error.message)
                            }
                    )
        }

       return rootView
        }
    fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tv_date.text = sdf.format(cal.time)
        projectData.date = tv_date.text.toString()
    }

    fun dynamicEditText(){
        val linearLayout = getView()?.findViewById<LinearLayout>(R.id.editTextContainer)

        // Create EditText
        val editText = EditText(requireContext())
        editText.setHint(R.string.enter_something)
        editText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.setPadding(20, 20, 20, 20)
        //editText.id = View.generateViewId()
        //val test = getView()!!.findViewById<EditText>(editText.id)
       //projectData.projectCode = editText.text.toString()
        Log.e(TAG,"code" + projectData.projectCode)

        // Add EditText to LinearLayout
        linearLayout?.addView(editText)
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable){
                str = editText.text.toString()
            }
        })
    }
}