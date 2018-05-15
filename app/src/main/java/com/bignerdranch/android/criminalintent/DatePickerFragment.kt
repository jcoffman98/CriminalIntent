package com.bignerdranch.android.criminalintent

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.DatePicker
import java.util.*

class DatePickerFragment : DialogFragment() {
    private lateinit var mDatePicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)

        val mDatePicker = v.findViewById(R.id.dialog_date_picker) as DatePicker
        mDatePicker.init(year, month, day, null)

        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        return builder.setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok,
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val year = mDatePicker.year
                        val month = mDatePicker.month
                        val day = mDatePicker.dayOfMonth
                        val date = GregorianCalendar(year, month, day).time
                        sendResult(Activity.RESULT_OK, date)
                    }
                }).create()
    }

    fun sendResult(resultCode: Int, date: Date) : Unit {

        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment.onActivityResult(targetRequestCode, resultCode, intent)
    }

     companion object {
        private const val ARG_DATE = "data"
        const val EXTRA_DATE = "com.bignerdranch.android.criminalintent.date"


        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            val fragment = DatePickerFragment()
            fragment.arguments = args

            return fragment
        }
    }
}